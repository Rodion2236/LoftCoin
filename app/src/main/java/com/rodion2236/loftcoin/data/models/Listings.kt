package com.rodion2236.loftcoin.data.models

import com.rodion2236.loftcoin.data.models.coin.CmcCoin
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Listings(
    val data: List<CmcCoin>
)