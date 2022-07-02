package com.rodion2236.loftcoin.ui.util

import dagger.Binds
import dagger.Module

@Module
abstract class UtilModule {

    @Binds
    abstract fun schedulers(impl: RxSchedulersImpl): RxSchedulers

    @Binds
    abstract fun notifier(impl: NotifierImpl): Notifier
}