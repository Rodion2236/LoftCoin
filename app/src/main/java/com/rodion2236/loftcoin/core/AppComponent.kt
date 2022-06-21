package com.rodion2236.loftcoin.core

import android.app.Application
import android.content.Context
import com.rodion2236.loftcoin.data.DataModule
import com.rodion2236.loftcoin.repository.CoinsRepo
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class
    ]
)
abstract class AppComponent: BaseComponent {

    abstract override fun context(): Context
    abstract override fun coinsRepo(): CoinsRepo

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(app: Application): Builder
        abstract fun build(): AppComponent
    }
}