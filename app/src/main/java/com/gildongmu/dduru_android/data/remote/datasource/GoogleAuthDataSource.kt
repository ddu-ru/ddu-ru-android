package com.gildongmu.dduru_android.data.remote.datasource
import android.content.Intent

interface GoogleAuthDataSource {
    fun getSignInIntent(): Intent
    fun getIdTokenFromIntent(data: Intent?): String?
}
