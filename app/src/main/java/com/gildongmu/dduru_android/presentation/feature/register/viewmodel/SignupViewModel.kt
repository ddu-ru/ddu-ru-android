package com.gildongmu.dduru_android.presentation.feature.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gildongmu.dduru_android.presentation.feature.register.state.CompletedItem
import com.gildongmu.dduru_android.presentation.feature.register.state.Gender
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupStep
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {

    var uiState by mutableStateOf(SignupUiState())
        private set

    val years = (1980..2024).toList()
    val months = (1..12).toList()
    val days = (1..31).toList()
    private val nicknameSamples = listOf(
        "겨울이요길",
        "푸른돌고래",
        "포근한별님",
        "상큼라임",
        "따뜻한눈송이"
    )

    fun onYearSelected(value: Int) {
        uiState = uiState.copy(year = value)
        checkBirthCompletion()
    }

    fun onMonthSelected(value: Int) {
        uiState = uiState.copy(month = value)
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
        if (nicknameSamples.isEmpty()) return
        val randomNickname = nicknameSamples.random()
        uiState = uiState.copy(nickname = randomNickname)
    }

    fun onNicknameComplete() {
        uiState = uiState.copy(
            completedItems = uiState.completedItems + CompletedItem(
                label = "닉네임",
                value = uiState.nickname
            ),
            step = SignupStep.PHONE
        )
    }


    fun onPhoneChange(value: String) {
        uiState = uiState.copy(phoneNumber = value)
    }

    private var authTimerJob: Job? = null

    fun onPhoneComplete() {
        val itemsWithoutPhone = uiState.completedItems.filterNot { it.label == "전화번호" }
        val ensuredNickname = if (itemsWithoutPhone.none { it.label == "닉네임" } && uiState.nickname.isNotBlank()) {
            itemsWithoutPhone + CompletedItem(label = "닉네임", value = uiState.nickname)
        } else {
            itemsWithoutPhone
        }
        val updatedItems = ensuredNickname + CompletedItem(
            label = "전화번호",
            value = uiState.phoneNumber
        )
        uiState = uiState.copy(
            completedItems = updatedItems,
            step = SignupStep.AUTH,
            isPhoneRequested = true,
            authTimerSeconds = 60,
            authCode = ""
        )
        startAuthTimer()
    }


    fun onAuthCodeChange(value: String) {
        uiState = uiState.copy(authCode = value)
    }

    fun onAuthComplete() {
        stopAuthTimer()
        uiState = uiState.copy(
            completedItems = uiState.completedItems + CompletedItem(
                label = "인증번호",
                value = "완료"
            ),
            step = SignupStep.DONE,
            authTimerSeconds = 0
        )
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
}
