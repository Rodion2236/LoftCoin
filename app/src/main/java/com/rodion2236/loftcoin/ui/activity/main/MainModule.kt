package com.rodion2236.loftcoin.ui.activity.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.rodion2236.loftcoin.ui.activity.converter.ConverterFragment
import com.rodion2236.loftcoin.ui.activity.rates.RatesFragment
import com.rodion2236.loftcoin.ui.activity.wallet.WalletsFragment
import com.rodion2236.loftcoin.ui.util.LoftFragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    abstract fun fragmentFactory(impl: LoftFragmentFactory): FragmentFactory

    @Binds
    @IntoMap
    @ClassKey(RatesFragment::class)
    abstract fun ratesFragment(impl: RatesFragment): Fragment

    @Binds
    @IntoMap
    @ClassKey(ConverterFragment::class)
    abstract fun converterFragment(impl: ConverterFragment): Fragment

    @Binds
    @IntoMap
    @ClassKey(WalletsFragment::class)
    abstract fun walletsFragment(impl: WalletsFragment): Fragment
}