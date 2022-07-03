package com.rodion2236.loftcoin.ui.activity.wallet

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class WalletModule {

    @Binds
    @IntoMap
    @ClassKey(WalletsViewModel::class)
    abstract fun walletsViewModel(impl: WalletsViewModel): ViewModel
}
