package com.rodion2236.loftcoin.core

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun viewModelFactory(impl: ViewModelFactory): ViewModelProvider.Factory
}