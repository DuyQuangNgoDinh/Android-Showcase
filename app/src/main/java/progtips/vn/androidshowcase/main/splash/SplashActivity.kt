package progtips.vn.androidshowcase.main.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import progtips.vn.androidshowcase.MainActivity
import progtips.vn.asia.firebaseconfig.AppConfig

/**
 * Splashscreen Activity
 */
class SplashActivity : AppCompatActivity() {
    private val appConfig = AppConfig(this)
    override fun onResume() {
        super.onResume()

        doTaskAndStartActivity()
    }

    private fun doTaskAndStartActivity() {
        appConfig.fetchConfig(
            onComplete = {
                // start mainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        )
    }
}