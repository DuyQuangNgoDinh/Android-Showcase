package progtips.vn.androidshowcase.main.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import progtips.vn.androidshowcase.MainActivity
import progtips.vn.androidshowcase.databinding.ActivityWelcomeBinding
import progtips.vn.androidshowcase.main.welcome.adapter.WelcomeAdapter

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private val welcomeViewModel: WelcomeViewModel by viewModels()
    private val adapter = WelcomeAdapter(object : WelcomeAdapter.OnClickWelcomeListener{
        override fun onClickSkip() {
            startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
            finish()
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initView()
    }

    private fun initView() {
        binding.pagerWelcome.adapter = adapter
        adapter.submitList(welcomeViewModel.getWelcomePages(this))
    }
}