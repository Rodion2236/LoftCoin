package com.rodion2236.loftcoin.ui.activity.currency

import androidx.lifecycle.ViewModelProvider
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.core.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CurrencyModule::class,
        ViewModelModule::class],
    dependencies = [
        BaseComponent::class
    ]
)
abstract class CurrencyComponent {

    abstract fun viewModelFactory(): ViewModelProvider.Factory
}