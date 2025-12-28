package com.gildongmu.dduru_android.presentation.feature.register.viewmodel

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.gildongmu.dduru_android.presentation.feature.register.state.SignupStep

@Composable
fun GreetingMessageCard(step: SignupStep) {
    val (title, desc) = when (step) {
        SignupStep.PHONE -> "전화번호를 입력해주세요" to "본인 확인을 위해 필요해요"
        SignupStep.AUTH -> "인증번호를 입력해주세요" to "문자로 받은 번호를 입력하세요"
        SignupStep.BIRTH -> "생년월일을 알려주세요" to "정확한 나이를 확인해요"
        SignupStep.GENDER -> "성별을 선택해주세요" to "추천에 사용돼요"
        else -> "" to ""
    }

    Column {
        Text(title)
        Text(desc)
    }
}
