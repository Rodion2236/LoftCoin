package com.rodion2236.loftcoin.core

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Singleton
    @Provides
    fun provideIoExecutor() =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2 + 1)
}