package com.rodion2236.loftcoin.data.models.coin

import com.squareup.moshi.Json

data class CmcCoin(
    override val id: Long,
    override val name: String,
    override val symbol: String,
    @Json(name = "cmc_rank")
    override val rank: Int,
    val quote: Map<String, Quote>
) : Coin {
    override val price: Double
        get() = quote.values.firstOrNull()?.price ?: 0.0

    override val change24h: Double
        get() = quote.values.firstOrNull()?.change24h ?: 0.0
}