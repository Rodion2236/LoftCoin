package com.rodion2236.loftcoin.ui.activity.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.rodion2236.loftcoin.LoftApp
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding
    private lateinit var component: MainComponent

    @Inject
    lateinit var fragmentFactory: FragmentFactory

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(context)
        component = DaggerMainComponent.builder()
            .baseComponent((context.applicationContext as LoftApp).component)
            .build()
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
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