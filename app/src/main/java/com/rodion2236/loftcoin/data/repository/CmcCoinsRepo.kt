package com.rodion2236.loftcoin.data.repository

import com.rodion2236.loftcoin.data.api.CmcApiRetrofit
import com.rodion2236.loftcoin.data.models.Coin
import java.io.IOException

class CmcCoinsRepo : CoinsRepo {

    lateinit var api: CmcApiRetrofit

    override fun listings(currency: String): List<Coin> {
        val result = api.cmcApi.listings(currency).execute()
        if (result.isSuccessful) {
            return result.body()?.data ?:
            throw IllegalStateException("")
        } else {
            throw IOException(result.errorBody()?.string() ?: "")
        }
    }
}