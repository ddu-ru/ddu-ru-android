package com.gildongmu.dduru.presentation.feature.register.viewmodel

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.R
import com.gildongmu.dduru.core.common.util.DateUtils
import com.gildongmu.dduru.data.local.TokenLocalDataSource
import com.gildongmu.dduru.data.remote.interceptor.TokenAuthenticator
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.phone.PhoneVerificationRepository
import com.gildongmu.dduru.domain.phone.VerificationTimers
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
import com.gildongmu.dduru.presentation.feature.register.state.AuthCodeValidationResult
import com.gildongmu.dduru.presentation.feature.register.state.CompletedItem
import com.gildongmu.dduru.presentation.feature.register.state.Gender
import com.gildongmu.dduru.presentation.feature.register.state.PhoneValidationResult
import com.gildongmu.dduru.presentation.feature.register.state.SignupBottomButtonState
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru.presentation.feature.register.state.SignupToast
import com.gildongmu.dduru.presentation.feature.register.state.SignupUiState
import com.gildongmu.dduru.presentation.feature.register.util.AuthCodeValidator
import com.gildongmu.dduru.presentation.feature.register.util.PhoneValidator
import com.gildongmu.dduru.presentation.feature.register.util.PhoneVerificationError
import com.gildongmu.dduru.presentation.feature.register.util.PhoneVerificationErrorMapper
import com.gildongmu.dduru.presentation.feature.register.util.ValidationMessageMapper.toMessageRes
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.math.ceil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val ALREADY_VERIFIED_CODE = "ALREADY_VERIFIED"
private const val INVALID_TOKEN_CODE = "INVALID_TOKEN"
private const val INVALID_INPUT_CODE = "INVALID_INPUT_VALUE"
private const val VERIFICATION_TOKEN_FIELD = "verificationToken"
private const val DEFAULT_AUTH_SECONDS = 180L
private const val TIMER_TICK_MILLIS = 1000L
private val KST_ZONE = ZoneId.of("Asia/Seoul")

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val phoneVerificationRepository: PhoneVerificationRepository,
    private val onboardingRegisterRepository: OnboardingRegisterRepository,
    private val tokenLocalDataSource: TokenLocalDataSource,
    private val tokenAuthenticator: TokenAuthenticator
) : ViewModel() {

    private val signupFlow = listOf(
        SignupStep.BIRTH,
        SignupStep.GENDER,
        SignupStep.PHONE,
        SignupStep.AUTH,
        SignupStep.AUTH_VERIFICATION,
        SignupStep.DONE
    )

    var uiState by mutableStateOf(SignupUiState().withDerivedState())
        private set

    private fun updateState(reducer: SignupUiState.() -> SignupUiState) {
        uiState = uiState.reducer().withDerivedState()
    }

    private fun SignupUiState.withDerivedState(): SignupUiState {
        val currentIndex = signupFlow.indexOf(step).takeIf { it >= 0 } ?: 0
        val visibleSteps = signupFlow.take(currentIndex + 1)

        val bottomButton = when (step) {
            SignupStep.PHONE -> SignupBottomButtonState(
                visible = true,
                text = "인증번호 전송",
                enabled = !isSendingCode && resendRemainingSeconds == 0
            )
            SignupStep.AUTH -> SignupBottomButtonState(
                visible = true,
                text = "확인",
                enabled = !isConfirmingCode
            )
            SignupStep.AUTH_VERIFICATION -> SignupBottomButtonState(
                visible = true,
                text = "다음",
                enabled = !isLoading
            )
            else -> SignupBottomButtonState()
        }

        val safeSeconds = authTimerSeconds.coerceAtLeast(0)
        val minutes = safeSeconds / 60
        val seconds = safeSeconds % 60
        val timerText = "%02d:%02d".format(minutes, seconds)

        return copy(
            visibleSteps = visibleSteps,
            bottomButton = bottomButton,
            timerText = timerText
        )
    }

    val years = (1940..LocalDate.now().minusYears(19).year).toList().reversed()
    val months = (1..12).toList()
    val days: List<Int>
        get() = DateUtils.getDayListInMonth(uiState.year, uiState.month)

    private var authTimerJob: Job? = null
    private var resendTimerJob: Job? = null

    fun onYearSelected(value: Int) {
        val currentMonth = uiState.month
        val currentDay = uiState.day
        if (currentMonth == null || currentDay == null) {
            updateState { copy(year = value) }
        } else {
            val correctedDay = DateUtils.getCorrectedDay(value, currentMonth, currentDay)
            updateState {
                copy(
                    year = value,
                    day = correctedDay
                )
            }
        }
        checkBirthCompletion()
    }

    fun onMonthSelected(value: Int) {
        val currentYear = uiState.year
        val currentDay = uiState.day
        if (currentYear == null || currentDay == null) {
            updateState { copy(month = value) }
        } else {
            val correctedDay = DateUtils.getCorrectedDay(currentYear, value, currentDay)
            updateState {
                copy(
                    month = value,
                    day = correctedDay
                )
            }
        }
        checkBirthCompletion()
    }

    fun onDaySelected(value: Int) {
        updateState { copy(day = value) }
        checkBirthCompletion()
    }

    private fun checkBirthCompletion() {
        val year = uiState.year
        val month = uiState.month
        val day = uiState.day
        if (year != null && month != null && day != null && uiState.step == SignupStep.BIRTH) {
            onBirthComplete()
        }
    }

    fun onBirthComplete() {
        updateState {
            copy(
                completedItems = completedItems.filterNot { it.label == "생년월일" } + CompletedItem(
                    label = "생년월일",
                    value = "$year.$month.$day"
                ),
                step = SignupStep.GENDER
            )
        }
    }

    fun onGenderSelect(gender: Gender) {
        val updatedItems = uiState.completedItems
            .filterNot { it.label == "성별" } + CompletedItem(
            label = "성별",
            value = if (gender == Gender.MALE) "남자" else "여자"
        )
        val nextStep = if (uiState.step.ordinal <= SignupStep.GENDER.ordinal) {
            SignupStep.PHONE
        } else {
            uiState.step
        }
        updateState {
            copy(
                gender = gender,
                genderErrorRes = null,
                completedItems = updatedItems,
                step = nextStep
            )
        }
    }

    fun onPhoneChange(value: String) {
        if (value != uiState.phoneNumber) stopResendTimer()
        updateState { copy(phoneNumber = value, phoneErrorRes = null) }
    }

    fun onPhoneComplete() {
        val result = PhoneValidator.validate(uiState.phoneNumber)

        if (result != PhoneValidationResult.VALID) {
            updateState { copy(phoneErrorRes = result.toMessageRes()) }
            return
        }

        if (uiState.isSendingCode || uiState.resendRemainingSeconds > 0) return

        viewModelScope.launch {
            updateState {
                copy(
                    isSendingCode = true,
                    phoneErrorRes = null,
                    authCodeErrorRes = null,
                    authCodeInfoRes = null
                )
            }

            if (restoreVerificationTimers()) {
                updateState { copy(isSendingCode = false) }
                return@launch
            }

            phoneVerificationRepository.sendVerificationCode(uiState.phoneNumber)
                .onSuccess { sendResult ->
                    updateState { copy(isSendingCode = false, authCode = "") }
                    moveToAuthStep()

                    val expiresAt = sendResult.expiresAt.toKstInstant()
                    val resendAvailableAt = sendResult.resendAvailableAt?.toOffsetInstant()
                    startAuthTimer(expiresAt)
                    resendAvailableAt?.let(::startResendTimer)
                    phoneVerificationRepository.saveVerificationTimers(
                        VerificationTimers(
                            phoneNumber = uiState.phoneNumber,
                            expiresAtMillis = expiresAt.toEpochMilli(),
                            resendAvailableAtMillis = resendAvailableAt?.toEpochMilli()
                        )
                    )
                }
                .onFailure { e ->
                    if (e is CancellationException) throw e

                    val error = e as? ExceptionResponse
                    updateState { copy(isSendingCode = false) }
                    error?.retryAfterSeconds?.let { seconds -> applyRetryAfter(seconds) }
                    showVerificationError(PhoneVerificationErrorMapper.toError(error))
                }
        }
    }

    private suspend fun restoreVerificationTimers(): Boolean {
        val timers = phoneVerificationRepository.getVerificationTimers(uiState.phoneNumber) ?: return false
        val now = System.currentTimeMillis()
        val expiresAtMillis = timers.expiresAtMillis?.takeIf { it > now } ?: return false
        val resendAvailableAtMillis = timers.resendAvailableAtMillis?.takeIf { it > now } ?: return false

        moveToAuthStep()
        startAuthTimer(Instant.ofEpochMilli(expiresAtMillis))
        startResendTimer(Instant.ofEpochMilli(resendAvailableAtMillis))
        return true
    }

    private suspend fun applyRetryAfter(seconds: Long) {
        val resendAvailableAt = Instant.now().plusSeconds(seconds)
        startResendTimer(resendAvailableAt)

        val savedExpiresAtMillis = phoneVerificationRepository
            .getVerificationTimers(uiState.phoneNumber)
            ?.expiresAtMillis
        savedExpiresAtMillis
            ?.takeIf { uiState.authTimerSeconds == 0 }
            ?.let { startAuthTimer(Instant.ofEpochMilli(it)) }
        phoneVerificationRepository.saveVerificationTimers(
            VerificationTimers(
                phoneNumber = uiState.phoneNumber,
                expiresAtMillis = savedExpiresAtMillis,
                resendAvailableAtMillis = resendAvailableAt.toEpochMilli()
            )
        )
    }

    private fun moveToAuthStep() {
        updateState {
            copy(
                completedItems = completedItems.filterNot { it.label == "전화번호" } + CompletedItem(
                    label = "전화번호",
                    value = phoneNumber
                ),
                step = SignupStep.AUTH,
                isPhoneRequested = true
            )
        }
    }

    fun onAuthCodeChange(value: String) {
        updateState { copy(authCode = value, authCodeErrorRes = null) }
    }

    fun onAuthComplete() {
        val result = AuthCodeValidator.validate(uiState.authCode)

        if (result != AuthCodeValidationResult.VALID) {
            updateState { copy(authCodeErrorRes = result.toMessageRes()) }
            return
        }

        if (uiState.isConfirmingCode) return

        viewModelScope.launch {
            updateState {
                copy(isConfirmingCode = true, authCodeErrorRes = null, authCodeInfoRes = null)
            }

            phoneVerificationRepository.confirmVerificationCode(
                phoneNumber = uiState.phoneNumber,
                verificationCode = uiState.authCode
            )
                .onSuccess {
                    updateState { copy(isConfirmingCode = false) }
                    phoneVerificationRepository.clearVerificationTimers()
                    moveToAuthVerified()
                }
                .onFailure { e ->
                    if (e is CancellationException) throw e

                    val error = e as? ExceptionResponse
                    updateState { copy(isConfirmingCode = false) }
                    if (error?.errorCode == ALREADY_VERIFIED_CODE) {
                        handleAlreadyVerified()
                    } else {
                        showVerificationError(PhoneVerificationErrorMapper.toError(error))
                    }
                }
        }
    }

    private suspend fun handleAlreadyVerified() {
        if (phoneVerificationRepository.hasValidVerificationToken(uiState.phoneNumber)) {
            moveToAuthVerified()
            updateState { copy(authCodeInfoRes = R.string.auth_code_info_already_verified) }
        } else {
            updateState { copy(authCodeErrorRes = R.string.phone_error_reverify) }
        }
    }

    private fun moveToAuthVerified() {
        stopAuthTimer()
        stopResendTimer()
        updateState {
            copy(
                authCodeErrorRes = null,
                completedItems = completedItems.filterNot { it.label == "인증번호" } + CompletedItem(
                    label = "인증번호",
                    value = "완료"
                ),
                step = SignupStep.AUTH_VERIFICATION,
                authTimerSeconds = 0
            )
        }
    }

    private fun showVerificationError(error: PhoneVerificationError) {
        when (error) {
            is PhoneVerificationError.Phone -> {
                if (uiState.step == SignupStep.AUTH_VERIFICATION) {
                    moveBackToPhone(error.messageRes)
                } else {
                    updateState { copy(phoneErrorRes = error.messageRes) }
                }
            }
            is PhoneVerificationError.AuthCode -> {
                if (uiState.step == SignupStep.PHONE) moveToAuthStep()
                updateState { copy(authCodeErrorRes = error.messageRes) }
            }
            is PhoneVerificationError.Birth -> {
                updateState { copy(birthErrorRes = error.messageRes) }
            }
            is PhoneVerificationError.Gender -> {
                updateState { copy(genderErrorRes = error.messageRes) }
            }
            is PhoneVerificationError.Toast -> {
                updateState { copy(toast = SignupToast(error.titleRes, error.messageRes)) }
            }
        }
    }

    private fun moveBackToPhone(@StringRes messageRes: Int) {
        stopAuthTimer()
        stopResendTimer()
        updateState {
            copy(
                step = SignupStep.PHONE,
                isPhoneRequested = false,
                authCode = "",
                authCodeErrorRes = null,
                authCodeInfoRes = null,
                authTimerSeconds = 0,
                phoneErrorRes = messageRes,
                completedItems = completedItems.filterNot { it.label == "전화번호" || it.label == "인증번호" }
            )
        }
        viewModelScope.launch {
            phoneVerificationRepository.clearVerificationToken()
            phoneVerificationRepository.clearVerificationTimers()
        }
    }

    private suspend fun handleInvalidVerificationToken() {
        when (checkLoginSession()) {
            LoginSessionState.VALID -> moveBackToPhone(R.string.phone_error_reverify)
            LoginSessionState.EXPIRED -> updateState { copy(isLoginExpired = true) }
            LoginSessionState.UNKNOWN -> showVerificationError(PhoneVerificationErrorMapper.toError(null))
        }
    }

    private suspend fun checkLoginSession(): LoginSessionState {
        val accessToken = tokenLocalDataSource.accessToken.first()
        if (accessToken.isNullOrBlank()) return LoginSessionState.EXPIRED
        if (!tokenAuthenticator.shouldRefresh(accessToken)) return LoginSessionState.VALID

        val refreshToken = tokenLocalDataSource.refreshToken.first()
        if (refreshToken.isNullOrBlank()) return LoginSessionState.EXPIRED

        val refreshedTokens = tokenAuthenticator.refreshAndSaveTokens(
            refreshToken = refreshToken,
            staleAccessToken = accessToken
        )
        return when {
            refreshedTokens != null -> LoginSessionState.VALID
            tokenLocalDataSource.accessToken.first().isNullOrBlank() -> LoginSessionState.EXPIRED
            else -> LoginSessionState.UNKNOWN
        }
    }

    fun onToastDismiss() {
        updateState { copy(toast = null) }
    }

    private fun startAuthTimer(endAt: Instant) {
        authTimerJob?.cancel()
        authTimerJob = viewModelScope.launch {
            do {
                val remaining = remainingSeconds(endAt)
                updateState { copy(authTimerSeconds = remaining) }
                if (remaining > 0) delay(TIMER_TICK_MILLIS)
            } while (remaining > 0)
        }
    }

    private fun stopAuthTimer() {
        authTimerJob?.cancel()
        authTimerJob = null
    }

    private fun startResendTimer(endAt: Instant) {
        resendTimerJob?.cancel()
        resendTimerJob = viewModelScope.launch {
            do {
                val remaining = remainingSeconds(endAt)
                updateState { copy(resendRemainingSeconds = remaining) }
                if (remaining > 0) delay(TIMER_TICK_MILLIS)
            } while (remaining > 0)
        }
    }

    private fun stopResendTimer() {
        resendTimerJob?.cancel()
        resendTimerJob = null
        updateState { copy(resendRemainingSeconds = 0) }
    }

    private fun remainingSeconds(endAt: Instant): Int {
        val millis = Duration.between(Instant.now(), endAt).toMillis()
        return ceil(millis / 1000.0).toInt().coerceAtLeast(0)
    }

    private fun String.toKstInstant(): Instant =
        runCatching { LocalDateTime.parse(this).atZone(KST_ZONE).toInstant() }
            .getOrElse { Instant.now().plusSeconds(DEFAULT_AUTH_SECONDS) }

    private fun String.toOffsetInstant(): Instant? =
        runCatching { OffsetDateTime.parse(this).toInstant() }.getOrNull()

    fun onRegisterComplete() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, birthErrorRes = null, genderErrorRes = null) }

            val genderValue = when (uiState.gender) {
                Gender.MALE -> "M"
                Gender.FEMALE -> "F"
                null -> {
                    updateState { copy(isLoading = false) }
                    return@launch
                }
            }

            val year = uiState.year ?: run {
                updateState { copy(isLoading = false) }
                return@launch
            }

            val month = uiState.month ?: run {
                updateState { copy(isLoading = false) }
                return@launch
            }

            val day = uiState.day ?: run {
                updateState { copy(isLoading = false) }
                return@launch
            }

            val birthday = "%04d-%02d-%02d".format(year, month, day)

            onboardingRegisterRepository.onboardingRegister(
                gender = genderValue,
                phoneNumber = uiState.phoneNumber,
                birthday = birthday
            ).onSuccess {
                updateState { copy(isLoading = false, step = SignupStep.DONE) }
            }.onFailure { e ->
                if (e is CancellationException) throw e

                val error = e as? ExceptionResponse
                updateState { copy(isLoading = false) }
                when {
                    e is IllegalStateException -> moveBackToPhone(R.string.phone_error_reverify)
                    error?.errorCode == INVALID_INPUT_CODE && error.field == VERIFICATION_TOKEN_FIELD ->
                        moveBackToPhone(R.string.phone_error_reverify)
                    error?.errorCode == INVALID_TOKEN_CODE -> handleInvalidVerificationToken()
                    else -> showVerificationError(PhoneVerificationErrorMapper.toError(error))
                }
            }
        }
    }
}

private enum class LoginSessionState {
    VALID,
    EXPIRED,
    UNKNOWN
}
