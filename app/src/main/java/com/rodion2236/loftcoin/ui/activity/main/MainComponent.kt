package com.rodion2236.loftcoin.ui.activity.main

import com.rodion2236.loftcoin.core.BaseComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        MainModule::class
    ],
    dependencies = [
        BaseComponent::class
    ]
)
abstract class MainComponent {

    abstract fun inject(activity: MainActivity)
}