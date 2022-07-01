package com.rodion2236.loftcoin.data.models.currency

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Currency(
    val symbol: String,
    val code: String,
    val name: String
)