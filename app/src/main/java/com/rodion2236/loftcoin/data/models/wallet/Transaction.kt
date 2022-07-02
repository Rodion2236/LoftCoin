package com.rodion2236.loftcoin.data.models.wallet

import com.rodion2236.loftcoin.data.models.coin.Coin
import java.util.*

data class Transaction(
    val uid: String,
    val coin: Coin,
    val amount: Double,
    val createdAt: Date
)