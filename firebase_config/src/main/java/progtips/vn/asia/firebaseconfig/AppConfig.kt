package progtips.vn.asia.firebaseconfig

import android.app.Activity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class AppConfig(
    private val activity: Activity
) {
    private val remoteConfig = Firebase.remoteConfig
    private val configSettings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }

    init {
        remoteConfig.setConfigSettingsAsync(configSettings)
    }

    fun getRemoteConfig() = remoteConfig

    fun fetchConfig(
        onComplete: () -> Unit = {},
        onCancel: () -> Unit = {},
        onError: () -> Unit = {}
    ) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) onComplete.invoke()
                else onError.invoke()
            }
            .addOnCanceledListener { onCancel.invoke() }
            .addOnFailureListener { onError.invoke() }
    }
}