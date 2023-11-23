package com.itsi.almuntaqimorevn

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.itsi.almuntaqimorevn.databinding.ActivityMainBinding
import com.itsi.almuntaqimorevn.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_splash)

        binding.tvSplashAppName.typeface = ResourcesCompat.getFont(this, com.itsi.almuntaqimorevn.R.font.amoshref_thulth_reguler)
        binding.tvSplashBookName.typeface = ResourcesCompat.getFont(this, com.itsi.almuntaqimorevn.R.font.amoshref_thulth_reguler)
        // on below line we are configuring
        // our window to full screen
        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/
        /*@Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }*/



        // on below line we are calling
        // handler to run a task
        // for specific time interval
        Handler(Looper.getMainLooper()).postDelayed({
            // on below line we are
            // creating a new intent
            val i = Intent(
                this@SplashActivity,
                MainActivity::class.java
            )
            // on below line we are
            // starting a new activity.
            startActivity(i)

            // on the below line we are finishing
            // our current activity.
            finish()
        }, 1000)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

    }

}