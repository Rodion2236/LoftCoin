package com.rodion2236.loftcoin.data.repository

import com.rodion2236.loftcoin.BuildConfig
import com.rodion2236.loftcoin.data.api.CmcApi
import com.rodion2236.loftcoin.data.models.Coin
import com.rodion2236.loftcoin.data.models.Listings
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class CmcCoinsRepo : CoinsRepo {

    val cmcApi: CmcApi

    init {
        cmcApi = configureRetrofit(configureOkHttpClient()).create(CmcApi::class.java)
    }

    override fun listings(currency: String): List<Coin> {
        val result = cmcApi.listings(currency).execute()
        if (result.isSuccessful) {
            return result.body()?.data ?:
            throw IllegalStateException("")
        } else {
            throw IOException(result.errorBody()?.string() ?: "")
        }
    }

    private fun configureOkHttpClient(): OkHttpClient {
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

    private fun configureRetrofit(configureOkHttpClient: OkHttpClient): Retrofit {
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
            .build()
    }
}
