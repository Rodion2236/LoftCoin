package com.rodion2236.loftcoin.ui.activity.rates

import androidx.lifecycle.ViewModelProvider
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.core.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RatesModule::class,
        ViewModelModule::class
    ],
    dependencies = [
        BaseComponent::class
    ]
)
abstract class RatesComponent {

    abstract fun viewModelFactory() : ViewModelProvider.Factory
}