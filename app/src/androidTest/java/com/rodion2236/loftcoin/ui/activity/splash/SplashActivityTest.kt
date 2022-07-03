package com.rodion2236.loftcoin.ui.activity.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.intercepting.SingleActivityFactory
import com.rodion2236.loftcoin.ui.activity.main.MainActivity
import com.rodion2236.loftcoin.ui.activity.welcome.WelcomeActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @get:Rule
    val rule = ActivityTestRule(
        object : SingleActivityFactory<SplashActivity>(SplashActivity::class.java) {
            override fun create(intent: Intent?): SplashActivity {
                return SplashActivity().apply {
                    idling = TestIdling
                }
            }

        }, false, false
    )

    private lateinit var prefs: SharedPreferences

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        IdlingRegistry.getInstance().register(TestIdling.resource)
    }

    @Test
    fun open_welcome_if_first_run() {
        prefs.edit().putBoolean(WelcomeActivity.KEY_SHOW_WELCOME_SCREEN,true).apply()
        rule.launchActivity(Intent())
        Intents.init()
        Intents.intended(IntentMatchers.hasComponent(WelcomeActivity::class.qualifiedName))
    }

    @Test
    fun open_main_if_start_working_being_clicked() {
        prefs.edit().putBoolean(WelcomeActivity.KEY_SHOW_WELCOME_SCREEN,false).apply()
        rule.launchActivity(Intent())
        Intents.init()
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.qualifiedName))
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(TestIdling.resource)
        Intents.release()
    }

    object TestIdling : SplashIdling {
        val resource = CountingIdlingResource("splash")

        override fun busy() {
            resource.increment()
        }

        override fun idle() {
            resource.decrement()
        }
    }
}