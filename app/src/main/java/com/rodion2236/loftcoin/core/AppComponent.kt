package com.rodion2236.loftcoin.core

import android.app.Application
import android.content.Context
import com.rodion2236.loftcoin.ui.util.DataModule
import com.rodion2236.loftcoin.repository.currency.CurrencyRepo
import com.rodion2236.loftcoin.repository.coin.CoinsRepo
import com.rodion2236.loftcoin.ui.util.UtilModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        UtilModule::class
    ]
)
abstract class AppComponent : BaseComponent {

    abstract override fun context(): Context
    abstract override fun coinsRepo(): CoinsRepo
    abstract override fun currencyRepo(): CurrencyRepo

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(app: Application): Builder
        abstract fun build(): AppComponent
    }
}