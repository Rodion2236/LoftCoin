package com.rodion2236.loftcoin.data.models.coin

interface Coin {
    val id: Int
    val name: String
    val symbol: String
    val rank: Int
    val price: Double
    val change24h: Double
}