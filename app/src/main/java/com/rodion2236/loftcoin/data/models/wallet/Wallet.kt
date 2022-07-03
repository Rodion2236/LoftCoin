package com.rodion2236.loftcoin.data.models.wallet

import com.rodion2236.loftcoin.data.models.coin.Coin

data class Wallet(
    private val uid: String,
    private val coin: Coin,
    private val balance: Double,
) {

    fun getUid(): String = uid
    fun getCoin(): Coin = coin
    fun getBalance(): Double = balance

    companion object {
        fun create(id: String, coin: Coin, balance: Double): Wallet {
            return Wallet(id, coin, balance)
        }
    }
}