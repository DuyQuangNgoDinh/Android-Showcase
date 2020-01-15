package progtips.vn.androidshowcase.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

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
        Thread.sleep(2000)
    }
}