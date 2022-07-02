package com.rodion2236.loftcoin.data.models.coin.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RoomCoin(
    @PrimaryKey
    val id: Int,
    val name: String,
    val symbol: String,
    val rank: String,
    val price: Double,
    val change24h: Double,
    val currencyCode: String
)