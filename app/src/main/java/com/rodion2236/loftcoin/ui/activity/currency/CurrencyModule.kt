package com.rodion2236.loftcoin.ui.activity.currency

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class CurrencyModule {
    @Binds
    @IntoMap
    @ClassKey(CurrencyViewModel::class)
    abstract fun currencyViewModel(impl: CurrencyViewModel): ViewModel?
}