package com.gildongmu.dduru_android.presentation.feature.register.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru_android.core.common.util.DateUtils
import com.gildongmu.dduru_android.domain.nickname.NicknameRepository
import com.gildongmu.dduru_android.domain.phone.PhoneVerificationErrorMapper
import com.gildongmu.dduru_android.domain.entity.ExceptionResponse
import com.gildongmu.dduru_android.domain.nickname.NicknameErrorMapper
import com.gildongmu.dduru_android.domain.phone.PhoneVerificationRepository
import com.gildongmu.dduru_android.domain.register.OnboardingRegisterRepository
import com.gildongmu.dduru_android.presentation.feature.register.state.AuthCodeValidationResult
import com.gildongmu.dduru_android.presentation.feature.register.state.CompletedItem
import com.gildongmu.dduru_android.presentation.feature.register.state.Gender
import com.gildongmu.dduru_android.presentation.feature.register.state.NicknameValidationResult
import com.gildongmu.dduru_android.presentation.feature.register.state.PhoneValidationResult
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupUiState
import com.gildongmu.dduru_android.presentation.feature.register.util.AuthCodeValidator
import com.gildongmu.dduru_android.presentation.feature.register.util.NicknameValidator
import com.gildongmu.dduru_android.presentation.feature.register.util.PhoneValidator
import com.gildongmu.dduru_android.presentation.feature.register.util.ValidationMessageMapper.toMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val nicknameRepository: NicknameRepository,
    private val phoneVerificationRepository: PhoneVerificationRepository,
    private val onboardingRegisterRepository: OnboardingRegisterRepository,
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
            SignupStep.NICKNAME
        } else {
            uiState.step
        }
        uiState = uiState.copy(
            gender = gender,
            completedItems = updatedItems,
            step = nextStep
        )
    }

    fun onNicknameChange(value: String) {
        uiState = uiState.copy(nickname = value)
    }

    fun onRandomNickname() {
        viewModelScope.launch {
            uiState = uiState.copy(
                nicknameErrorMessage = ""
            )

            nicknameRepository.getRandomNickname()
                .onSuccess { nickname ->
                    uiState = uiState.copy(
                        nickname = nickname,
                    )
                }
                .onFailure { e ->
                    val status = (e as? ExceptionResponse)?.status

                    uiState = uiState.copy(
                        nicknameErrorMessage = NicknameErrorMapper.toMessage(
                            status = status,
                        )
                    )
                }
        }
    }

    fun onNicknameComplete() {
        val result = NicknameValidator.validate(uiState.nickname)

        if (result != NicknameValidationResult.VALID) {
            uiState = uiState.copy(
                nicknameErrorMessage = result.toMessage()
            )
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(
                nicknameErrorMessage = ""
            )

            nicknameRepository.sanitizeNickname(uiState.nickname)
                .onSuccess { sanitized ->
                    uiState = uiState.copy(
                        nickname = uiState.nickname,
                        nicknameErrorMessage = "",
                        completedItems = uiState.completedItems + CompletedItem(
                            label = "닉네임",
                            value = uiState.nickname
                        ),
                        step = SignupStep.PHONE
                    )
                }
                .onFailure { e ->
                    val status = (e as? ExceptionResponse)?.status

                    uiState = uiState.copy(
                        nicknameErrorMessage = NicknameErrorMapper.toMessage(
                            status = status,
                        )
                    )
                }
        }
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
                    val ensuredNickname = if (
                        itemsWithoutPhone.none { it.label == "닉네임" } && uiState.nickname.isNotBlank()
                    ) {
                        itemsWithoutPhone + CompletedItem(label = "닉네임", value = uiState.nickname)
                    } else {
                        itemsWithoutPhone
                    }
                    val updatedItems = ensuredNickname + CompletedItem(
                        label = "전화번호",
                        value = uiState.phoneNumber
                    )

                    uiState = uiState.copy(
                        phoneErrorMessage = "",
                        completedItems = updatedItems,
                        step = SignupStep.AUTH,
                        isPhoneRequested = true,
                        authTimerSeconds = 180,
                        authCode = "",
                    )
                    startAuthTimer()
                }
                .onFailure { e ->
                    val status = (e as? ExceptionResponse)?.status
                    uiState = uiState.copy(
                        phoneErrorMessage = PhoneVerificationErrorMapper.sendErrorMessage(
                            status = status,
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
                            status = status,
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
            uiState = uiState.copy()

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

            onboardingRegisterRepository.initProfile(
                nickname = uiState.nickname,
                gender = genderValue,
                phoneNumber = uiState.phoneNumber,
                birthday = birthday
            ).onSuccess {
                uiState = uiState.copy(isLoading = false, step = SignupStep.DONE)
            }.onFailure { e ->
                uiState = uiState.copy(
                    isLoading = false,
//                    profileErrorMessage = ProfileInitErrorMapper.toMessage(
//                        status = status,
//                        serverMessage = e.message
//                    )
                )
            }
        }
    }
}
