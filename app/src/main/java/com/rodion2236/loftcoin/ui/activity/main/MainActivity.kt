package com.rodion2236.loftcoin.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bindingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(bindingMainActivity.toolbar)
        setContentView(bindingMainActivity.root)

        val navigationController = Navigation.findNavController(this, R.id.main_host)
        setupWithNavController(bindingMainActivity.bottomNav, navigationController)
        setupWithNavController(
            bindingMainActivity.toolbar,
            navigationController,
            AppBarConfiguration.Builder(bindingMainActivity.bottomNav.menu)
                .build()
        )
    }
}