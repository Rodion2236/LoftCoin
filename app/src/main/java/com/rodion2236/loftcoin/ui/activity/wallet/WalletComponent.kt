package com.rodion2236.loftcoin.ui.activity.wallet

import androidx.lifecycle.ViewModelProvider
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.core.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        WalletModule::class,
        ViewModelModule::class
              ],
    dependencies = [
        BaseComponent::class
    ]
)
internal abstract class WalletsComponent {
    abstract fun viewModelFactory(): ViewModelProvider.Factory
    abstract fun walletsAdapter(): WalletAdapter
}