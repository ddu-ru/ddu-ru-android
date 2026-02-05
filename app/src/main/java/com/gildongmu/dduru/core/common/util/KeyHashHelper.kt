package com.gildongmu.dduru.core.common.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest

object KeyHashHelper {

    fun printKeyHash(context: Context) {
        try {
            val packageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNING_CERTIFICATES
                )
            } else {
                @Suppress("DEPRECATION")
                context.packageManager.getPackageInfo(
                    context.packageName,
                    PackageManager.GET_SIGNATURES
                )
            }

            val signatures = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.signingInfo?.apkContentsSigners
            } else {
                @Suppress("DEPRECATION")
                packageInfo.signatures
            }

            signatures?.forEach { signature ->
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val keyHash = Base64.encodeToString(md.digest(), Base64.NO_WRAP)

                Log.d("KeyHash", "========================================")
                Log.d("KeyHash", "카카오 키 해시: $keyHash")
                Log.d("KeyHash", "========================================")
                Log.d("KeyHash", "이 값을 카카오 개발자 콘솔에 등록하세요!")
                Log.d("KeyHash", "앱 설정 → 플랫폼 → Android → 키 해시")
                Log.d("KeyHash", "========================================")
            }
        } catch (e: Exception) {
            Log.e("KeyHash", "키 해시 생성 실패", e)
        }
    }
}
