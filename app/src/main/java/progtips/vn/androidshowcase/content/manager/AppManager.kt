package progtips.vn.androidshowcase.content.manager

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    companion object {
        const val PREF_SHOULD_SHOW_WELCOME = "PREF_SHOULD_SHOW_WELCOME"
    }

    val shouldShowWelcomePageFlow = dataStore.data
        .map { preferences ->
            val shouldShowWelcomeKey = booleanPreferencesKey(PREF_SHOULD_SHOW_WELCOME)
            preferences[shouldShowWelcomeKey] ?: true
        }

    suspend fun updateShouldShowWelcomePage(status: Boolean) {
        dataStore.edit { settings ->
            val shouldShowWelcomeKey = booleanPreferencesKey(PREF_SHOULD_SHOW_WELCOME)
            settings[shouldShowWelcomeKey] = status
        }
    }
}