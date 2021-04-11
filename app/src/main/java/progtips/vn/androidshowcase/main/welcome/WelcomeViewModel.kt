package progtips.vn.androidshowcase.main.welcome

import android.app.Activity
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import progtips.vn.androidshowcase.BaseViewModel
import progtips.vn.androidshowcase.content.manager.AppManager
import progtips.vn.asia.firebaseconfig.AppConfig
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val appManager: AppManager
): BaseViewModel() {
    val shouldShowWelcomeLiveData = appManager.shouldShowWelcomePageFlow.asLiveData()

    fun updateShouldShowWelcome(status: Boolean) = launch {
        withContext(NonCancellable) {
            appManager.updateShouldShowWelcomePage(status)
        }
    }

    fun getWelcomePages(activity: Activity): List<WelcomePage> {
        val remoteConfig = AppConfig(activity).getRemoteConfig()

        return listOf(
            WelcomePage(
                remoteConfig.getString("welcome_title_1"),
                remoteConfig.getString("welcome_description_1")
            ),
            WelcomePage(
                remoteConfig.getString("welcome_title_2"),
                remoteConfig.getString("welcome_description_2")
            )
        )
    }

}