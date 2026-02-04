package com.gildongmu.dduru_android.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.verificationDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "verification_token"
)

class VerificationTokenLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : VerificationTokenLocalDataSource {

    companion object {
        private val VERIFICATION_TOKEN_KEY = stringPreferencesKey("verification_token")
    }

    override val verificationToken: Flow<String?> =
        context.verificationDataStore.data.map { prefs ->
            prefs[VERIFICATION_TOKEN_KEY]
        }

    override suspend fun saveVerificationToken(token: String) {
        context.verificationDataStore.edit { prefs ->
            prefs[VERIFICATION_TOKEN_KEY] = token
        }
    }

    override suspend fun clearVerificationToken() {
        context.verificationDataStore.edit { prefs ->
            prefs.remove(VERIFICATION_TOKEN_KEY)
        }
    }
}