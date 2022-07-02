package com.rodion2236.loftcoin.ui.activity.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.VisibleForTesting
import androidx.preference.PreferenceManager
import com.rodion2236.loftcoin.ui.activity.main.MainActivity
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.ui.activity.welcome.WelcomeActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable

    @VisibleForTesting
    var idling = object : SplashIdling {
        override fun busy() {}

        override fun idle() {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        runnable = if (sharedPreferences.getBoolean(WelcomeActivity.KEY_SHOW_WELCOME_SCREEN, true)) {
            Runnable {
                startActivity(Intent(this, WelcomeActivity::class.java))
                idling.idle()
            }
        } else {
            Runnable {
                startActivity(Intent(this, MainActivity::class.java))
                idling.idle()
            }
        }
        handler.postDelayed(runnable, 1500)
        idling.busy()
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }
}