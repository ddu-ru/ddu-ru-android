package com.gildongmu.dduru_android.data.remote.datasource

import android.content.Context

interface GoogleAuthDataSource {
    suspend fun getGoogleIdToken(context: Context): String?
}
