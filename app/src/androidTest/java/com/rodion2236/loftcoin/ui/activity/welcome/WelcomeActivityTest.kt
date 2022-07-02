package com.rodion2236.loftcoin.ui.activity.welcome

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodion2236.loftcoin.ui.activity.main.MainActivity
import com.rodion2236.loftcoin.R
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WelcomeActivityTest {

    @Test
    fun open_main_if_button_being_pressed() {
        launch(WelcomeActivity::class.java)
        Intents.init()
        Espresso.onView(withId(R.id.btn_start_working)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.qualifiedName))
    }

    @After
    fun tearDown() {
        Intents.release()
    }
}