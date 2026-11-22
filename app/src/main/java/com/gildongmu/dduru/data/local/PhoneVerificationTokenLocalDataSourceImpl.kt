package com.gildongmu.dduru.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gildongmu.dduru.domain.phone.VerificationTimers
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.verificationDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "verification_token"
)

class PhoneVerificationTokenLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PhoneVerificationTokenLocalDataSource {

    companion object {
        private val VERIFICATION_TOKEN_KEY = stringPreferencesKey("verification_token")
        private val TIMER_PHONE_NUMBER_KEY = stringPreferencesKey("timer_phone_number")
        private val TIMER_EXPIRES_AT_KEY = longPreferencesKey("timer_expires_at")
        private val TIMER_RESEND_AVAILABLE_AT_KEY = longPreferencesKey("timer_resend_available_at")
    }

    override val verificationToken: Flow<String?> =
        context.verificationDataStore.data.map { prefs ->
            prefs[VERIFICATION_TOKEN_KEY]
        }

    override val verificationTimers: Flow<VerificationTimers?> =
        context.verificationDataStore.data.map { prefs ->
            prefs[TIMER_PHONE_NUMBER_KEY]?.let { phoneNumber ->
                VerificationTimers(
                    phoneNumber = phoneNumber,
                    expiresAtMillis = prefs[TIMER_EXPIRES_AT_KEY],
                    resendAvailableAtMillis = prefs[TIMER_RESEND_AVAILABLE_AT_KEY]
                )
            }
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

    override suspend fun saveVerificationTimers(timers: VerificationTimers) {
        context.verificationDataStore.edit { prefs ->
            prefs[TIMER_PHONE_NUMBER_KEY] = timers.phoneNumber
            timers.expiresAtMillis?.let { prefs[TIMER_EXPIRES_AT_KEY] = it } ?: prefs.remove(TIMER_EXPIRES_AT_KEY)
            timers.resendAvailableAtMillis?.let { prefs[TIMER_RESEND_AVAILABLE_AT_KEY] = it }
                ?: prefs.remove(TIMER_RESEND_AVAILABLE_AT_KEY)
        }
    }

    override suspend fun clearVerificationTimers() {
        context.verificationDataStore.edit { prefs ->
            prefs.remove(TIMER_PHONE_NUMBER_KEY)
            prefs.remove(TIMER_EXPIRES_AT_KEY)
            prefs.remove(TIMER_RESEND_AVAILABLE_AT_KEY)
        }
    }
}
