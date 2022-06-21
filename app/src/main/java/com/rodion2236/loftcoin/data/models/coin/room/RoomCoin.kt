package com.rodion2236.loftcoin.data.models.coin.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rodion2236.loftcoin.data.models.coin.Coin

@Entity
data class RoomCoin(
    @PrimaryKey
    override val id: Int,
    override val name: String,
    override val symbol: String,
    override val rank: Int,
    override val price: Double,
    override val change24h: Double
): Coin