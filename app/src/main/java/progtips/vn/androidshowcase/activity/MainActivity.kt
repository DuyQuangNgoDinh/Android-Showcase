package progtips.vn.androidshowcase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import progtips.vn.androidshowcase.R

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration by lazy { AppBarConfiguration(navController.graph) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configToolbar()
    }

    private fun configToolbar() {
//        toolbar.setupWithNavController(navController, appBarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return when(navController.currentDestination?.id) {
            R.id.loginFragment -> {
                navController.popBackStack(R.id.catalogueFragment, false)
                true
            }
            else -> navController.navigateUp() || super.onSupportNavigateUp()
        }
    }

    override fun onBackPressed() {
        when(navController.currentDestination?.id) {
            R.id.loginFragment -> {
                navController.popBackStack(R.id.catalogueFragment, false)
            }
            else -> super.onBackPressed()
        }
    }
}
