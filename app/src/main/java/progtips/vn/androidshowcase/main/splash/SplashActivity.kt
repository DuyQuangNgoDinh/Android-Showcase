package progtips.vn.androidshowcase.main.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.MainActivity
import progtips.vn.androidshowcase.main.welcome.WelcomeActivity
import progtips.vn.androidshowcase.main.welcome.WelcomeViewModel
import progtips.vn.asia.firebaseconfig.AppConfig
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject lateinit var appConfig: AppConfig
    private val welcomeViewModel: WelcomeViewModel by viewModels()
    private var shouldShowWelcome: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerObserver()
    }

    override fun onResume() {
        super.onResume()
        doTaskAndStartActivity()
    }

    private fun doTaskAndStartActivity() {
        appConfig.fetchConfig(
            onComplete = {
                val nextScreen =
                    if (shouldShowWelcome) WelcomeActivity::class.java
                    else MainActivity::class.java

                startActivity(Intent(this, nextScreen))
                finish()
            }
        )
    }

    private fun registerObserver() {
        welcomeViewModel.shouldShowWelcomeLiveData.observe(this) {
            shouldShowWelcome = it
        }
    }
}