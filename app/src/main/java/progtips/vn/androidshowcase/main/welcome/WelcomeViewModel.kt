package progtips.vn.androidshowcase.main.welcome

import android.app.Activity
import android.content.Context
import dagger.hilt.android.lifecycle.HiltViewModel
import progtips.vn.androidshowcase.BaseViewModel
import progtips.vn.asia.firebaseconfig.AppConfig
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(): BaseViewModel() {

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