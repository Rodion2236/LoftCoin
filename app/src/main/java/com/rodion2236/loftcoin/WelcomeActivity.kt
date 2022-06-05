package com.rodion2236.loftcoin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.rodion2236.loftcoin.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var bindingWelcomeActivity: ActivityWelcomeBinding
    lateinit var snapHelper: SnapHelper

    companion object {
        const val KEY_SHOW_WELCOME_SCREEN = "KSW"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingWelcomeActivity = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(bindingWelcomeActivity.root)

        bindingWelcomeActivity.recycler.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        bindingWelcomeActivity.recycler.addItemDecoration(CircleIndicator(this))
        bindingWelcomeActivity.recycler.adapter = WelcomeRecyclerViewAdapter()
        bindingWelcomeActivity.recycler.setHasFixedSize(true)

        snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(bindingWelcomeActivity.recycler)

        bindingWelcomeActivity.btnStartWorking.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean(KEY_SHOW_WELCOME_SCREEN,false)
                .apply()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    override fun onDestroy() {
        snapHelper.attachToRecyclerView(null)
        bindingWelcomeActivity.recycler.adapter = null
        super.onDestroy()
    }
}