package com.rodion2236.loftcoin.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coin(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: Double,
    val change24h: Double
)