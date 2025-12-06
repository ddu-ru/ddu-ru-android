package com.gildongmu.dduru_android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class DduRuApplication : Application() {

    companion object {
        lateinit var instance: DduRuApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        // Timber 초기화 (로그용)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
        Timber.d("Kakao SDK 초기화 완료: ${BuildConfig.KAKAO_NATIVE_APP_KEY}")
    }
}
