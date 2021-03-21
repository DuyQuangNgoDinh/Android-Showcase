package progtips.vn.androidshowcase.main.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import progtips.vn.androidshowcase.MainActivity

/**
 * Splashscreen Activity
 */
class SplashActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()

        doTaskAndStartActivity()
    }

    private fun doTaskAndStartActivity() {
        // do some tasks when in splashscreen
        doTask()

        // start mainActivity
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun doTask() {
        Thread.sleep(0)
    }
}