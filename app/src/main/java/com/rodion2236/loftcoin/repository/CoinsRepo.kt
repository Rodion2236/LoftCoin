package com.rodion2236.loftcoin.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.CoinQuery

interface CoinsRepo {

    fun listings(query: Query): LiveData<List<Coin>>

    data class Query(
        val currency: String,
        val forceUpdate: Boolean,
        val sortBy: SortBy
    ) {
        private constructor(builder: Builder) : this(builder.currency!!, builder.forceUpdate!!, builder.sortBy!!)

        data class Builder(
            var currency: String? = null,
            var forceUpdate: Boolean? = null,
            var sortBy: SortBy? = null) {

            fun currency(currency: String) = apply { this.currency = currency }
            fun forceUpdate(forceUpdate: Boolean) = apply { this.forceUpdate = forceUpdate }
            fun sortBy(sortBy: SortBy) = apply { this.sortBy = sortBy }
            fun build() = Query(this)
        }
    }
}