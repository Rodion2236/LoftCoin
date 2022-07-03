package com.rodion2236.loftcoin.data

import com.rodion2236.loftcoin.data.models.coin.Coin


class FakeCoin: Coin() {
    override val id: Int = 0
    override val name: String = ""
    override val symbol: String = ""
    override val rank: String = ""
    override val price: Double = 0.0
    override val change24h: Double = 0.0
}