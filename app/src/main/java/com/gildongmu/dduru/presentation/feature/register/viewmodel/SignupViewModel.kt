package com.gildongmu.dduru.presentation.feature.register.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru.core.common.util.DateUtils
import com.gildongmu.dduru.domain.entity.ExceptionResponse
import com.gildongmu.dduru.domain.phone.PhoneVerificationErrorMapper
import com.gildongmu.dduru.domain.phone.PhoneVerificationRepository
import com.gildongmu.dduru.domain.register.OnboardingRegisterErrorMapper
import com.gildongmu.dduru.domain.register.OnboardingRegisterRepository
import com.gildongmu.dduru.presentation.feature.register.state.AuthCodeValidationResult
import com.gildongmu.dduru.presentation.feature.register.state.CompletedItem
import com.gildongmu.dduru.presentation.feature.register.state.Gender
import com.gildongmu.dduru.presentation.feature.register.state.PhoneValidationResult
import com.gildongmu.dduru.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru.presentation.feature.register.state.SignupUiState
import com.gildongmu.dduru.presentation.feature.register.util.AuthCodeValidator
import com.gildongmu.dduru.presentation.feature.register.util.PhoneValidator
import com.gildongmu.dduru.presentation.feature.register.util.ValidationMessageMapper.toMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val phoneVerificationRepository: PhoneVerificationRepository,
    private val onboardingRegisterRepository: OnboardingRegisterRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignupUiState())
        private set

    val years = (1940..2024).toList().reversed()
    val months = (1..12).toList()
    val days: List<Int>
        get() = DateUtils.getDayListInMonth(uiState.year, uiState.month)

    private var authTimerJob: Job? = null

    fun onYearSelected(value: Int) {
        val currentMonth = uiState.month
        val currentDay = uiState.day
        if (currentMonth == null || currentDay == null) {
            uiState = uiState.copy(year = value)
        } else {
            val correctedDay = DateUtils.getCorrectedDay(value, currentMonth, currentDay)
            uiState = uiState.copy(
                year = value,
                day = correctedDay
            )
        }
        checkBirthCompletion()
    }

    fun onMonthSelected(value: Int) {
        val currentYear = uiState.year
        val currentDay = uiState.day
        if (currentYear == null || currentDay == null) {
            uiState = uiState.copy(month = value)
        } else {
            val correctedDay = DateUtils.getCorrectedDay(currentYear, value, currentDay)
            uiState = uiState.copy(
                month = value,
                day = correctedDay
            )
        }
        checkBirthCompletion()
    }

    fun onDaySelected(value: Int) {
        uiState = uiState.copy(day = value)
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
        uiState = uiState.copy(
            completedItems = uiState.completedItems + CompletedItem(
                label = "생년월일",
                value = "${uiState.year}.${uiState.month}.${uiState.day}"
            ),
            step = SignupStep.GENDER
        )
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
        uiState = uiState.copy(
            gender = gender,
            completedItems = updatedItems,
            step = nextStep
        )
    }

    fun onPhoneChange(value: String) {
        uiState = uiState.copy(phoneNumber = value)
    }

    fun onPhoneComplete() {
        val result = PhoneValidator.validate(uiState.phoneNumber)

        if (result != PhoneValidationResult.VALID) {
            uiState = uiState.copy(phoneErrorMessage = result.toMessage())
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(phoneErrorMessage = "")

            phoneVerificationRepository.sendVerificationCode(uiState.phoneNumber)
                .onSuccess {
                    val itemsWithoutPhone = uiState.completedItems.filterNot { it.label == "전화번호" }
                    val updatedItems = itemsWithoutPhone + CompletedItem(
                        label = "전화번호",
                        value = uiState.phoneNumber
                    )

                    uiState = uiState.copy(
                        phoneErrorMessage = "",
                        completedItems = updatedItems,
                        step = SignupStep.AUTH,
                        isPhoneRequested = true,
                        authTimerSeconds = 180,
                        authCode = ""
                    )
                    startAuthTimer()
                }
                .onFailure { e ->
                    val status = (e as? ExceptionResponse)?.status
                    uiState = uiState.copy(
                        phoneErrorMessage = PhoneVerificationErrorMapper.sendErrorMessage(
                            status = status
                        )
                    )
                }
        }
    }

    fun onAuthCodeChange(value: String) {
        uiState = uiState.copy(authCode = value)
    }

    fun onAuthComplete() {
        val result = AuthCodeValidator.validate(uiState.authCode, uiState.authTimerSeconds)

        if (result != AuthCodeValidationResult.VALID) {
            uiState = uiState.copy(authCodeErrorMessage = result.toMessage())
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(authCodeErrorMessage = "")

            phoneVerificationRepository.confirmVerificationCode(
                phoneNumber = uiState.phoneNumber,
                verificationCode = uiState.authCode
            )
                .onSuccess {
                    stopAuthTimer()
                    uiState = uiState.copy(
                        authCodeErrorMessage = "",
                        completedItems = uiState.completedItems + CompletedItem(
                            label = "인증번호",
                            value = "완료"
                        ),
                        step = SignupStep.AUTH_VERIFICATION,
                        authTimerSeconds = 0
                    )
                }
                .onFailure { e ->
                    val status = (e as? ExceptionResponse)?.status
                    uiState = uiState.copy(
                        authCodeErrorMessage = PhoneVerificationErrorMapper.confirmErrorMessage(
                            status = status
                        )
                    )
                }
        }
    }

    private fun startAuthTimer() {
        authTimerJob?.cancel()
        authTimerJob = viewModelScope.launch {
            var seconds = uiState.authTimerSeconds
            if (seconds <= 0) seconds = 60
            while (seconds >= 0) {
                uiState = uiState.copy(authTimerSeconds = seconds)
                delay(1000L)
                seconds--
            }
        }
    }

    private fun stopAuthTimer() {
        authTimerJob?.cancel()
        authTimerJob = null
    }

    fun onRegisterComplete() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            val genderValue = when (uiState.gender) {
                Gender.MALE -> "M"
                Gender.FEMALE -> "F"
                null -> {
                    uiState = uiState.copy(isLoading = false)
                    return@launch
                }
            }

            val year = uiState.year ?: run {
                uiState = uiState.copy(isLoading = false)
                return@launch
            }

            val month = uiState.month ?: run {
                uiState = uiState.copy(isLoading = false)
                return@launch
            }

            val day = uiState.day ?: run {
                uiState = uiState.copy(isLoading = false)
                return@launch
            }

            val birthday = "%04d-%02d-%02d".format(year, month, day)

            onboardingRegisterRepository.onboardingRegister(
                gender = genderValue,
                phoneNumber = uiState.phoneNumber,
                birthday = birthday
            ).onSuccess {
                uiState = uiState.copy(isLoading = false, step = SignupStep.DONE)
            }.onFailure { e ->
                if (e is CancellationException) throw e

                val errorMessage = when (e) {
                    is ExceptionResponse -> {
                        OnboardingRegisterErrorMapper.toMessage(e.status)
                    }
                    is IllegalStateException -> {
                        e.message ?: "프로필 설정에 실패했어요."
                    }
                    else -> {
                        "프로필 설정에 실패했어요."
                    }
                }

                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = errorMessage
                )
            }
        }
    }
}
