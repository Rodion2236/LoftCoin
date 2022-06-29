package com.rodion2236.loftcoin.data.models.coin

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CmcCoin(
    val id: Int,
    val name: String,
    val symbol: String,
    @Json(name = "cmc_rank")
    val rank: String,
    val quote: Map<String, Quote>
)

@JsonClass(generateAdapter = true)
data class Quote(
    val price: Double,
    @Json(name = "percent_change_24h")
    val change24h: Double
)



data class Coin(
    val id: Int,
    val name: String,
    val symbol: String,
    val rank: String,
    val price: Double,
    val change24h: Double,
    val currencyCode: String
)