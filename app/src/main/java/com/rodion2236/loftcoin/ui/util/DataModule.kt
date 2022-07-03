package com.rodion2236.loftcoin.ui.util

import android.content.Context
import androidx.room.Room
import com.rodion2236.loftcoin.BuildConfig
import com.rodion2236.loftcoin.data.api.CmcApi
import com.rodion2236.loftcoin.data.models.listings.Listings
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.room.LoftCoinDatabase
import com.rodion2236.loftcoin.repository.currency.CurrencyRepo
import com.rodion2236.loftcoin.repository.currency.CurrencyRepoImpl
import com.rodion2236.loftcoin.repository.coin.CmcCoinsRepo
import com.rodion2236.loftcoin.repository.coin.CoinsRepo
import com.rodion2236.loftcoin.repository.wallets.WalletsRepo
import com.rodion2236.loftcoin.repository.wallets.WalletsRepoImpl
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Module
    companion object {

        @Singleton
        @Provides
        fun configureOkHttpClient(): OkHttpClient {
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request = chain.request()
                chain.proceed(
                    request.newBuilder()
                        .addHeader(CmcApi.API_KEY, BuildConfig.API_KEY)
                        .build()
                )
            })
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
                interceptor.redactHeader(BuildConfig.API_KEY)
                clientBuilder.addInterceptor(interceptor)
            }
            return clientBuilder.build()
        }

        @Provides
        fun configureRetrofit(configureOkHttpClient: OkHttpClient): Retrofit{
            val moshiBuilder = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
            return Retrofit.Builder()
                .client(configureOkHttpClient)
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder()
                            .add(Coin::class.java, moshiBuilder.adapter(Coin::class.java))
                            .add(Listings::class.java, moshiBuilder.adapter(Listings::class.java))
                            .build()
                    )
                )
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build()
        }

        @Provides
        fun getCmcApi(retrofit: Retrofit): CmcApi = retrofit.create(CmcApi::class.java)

        @Provides
        @Singleton
        fun loftDatabase(context: Context) =
            Room.databaseBuilder(context, LoftCoinDatabase::class.java, "loftcoin.db").build()
    }

    @Binds
    abstract fun bindCoinsRepo(repo: CmcCoinsRepo): CoinsRepo

    @Binds
    abstract fun bindCurrencyRepo(currencyRepo: CurrencyRepoImpl): CurrencyRepo

    @Binds
    abstract fun bindWalletsRepo(repo: WalletsRepoImpl) : WalletsRepo
}