package gildong.dduru.core.common.util

import android.content.Context

// 로그인 타입을 구분하기 위한 열거형
enum class SocialType { KAKAO, GOOGLE }

// 결과 콜백 인터페이스
interface SocialLoginCallback {
    fun onSuccess(idToken: String)
    fun onFailure(errorMessage: String? = null)
}

// 공통 헬퍼 클래스
abstract class SocialLoginHelper(val tag: String = "SocialLogin") {
    abstract fun login(context: Context, callback: SocialLoginCallback)
}
