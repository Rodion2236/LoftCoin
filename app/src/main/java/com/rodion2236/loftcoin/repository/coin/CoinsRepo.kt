package com.rodion2236.loftcoin.repository.coin

import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.currency.Currency
import io.reactivex.Observable
import io.reactivex.Single

interface CoinsRepo {

    fun listings(query: Query): Observable<List<Coin>>
    fun coin(currency: Currency, id: Long): Single<Coin>

    data class Query(
        val currency: String?,
        val forceRefresh: Boolean?,
        val sortBy: SortBy?,
    ) {
        companion object Builder {
            private var currency: String? = null
            private var forceRefresh: Boolean? = null
            private var sortBy: SortBy? = null

            fun currency(value: String) = apply { currency = value }
            fun forceRefresh(value: Boolean) = apply { forceRefresh = value }
            fun sortBy(value: SortBy) = apply { sortBy = value }

            fun build(): Query {
                return Query(
                    currency,
                    forceRefresh,
                    sortBy
                )
            }
        }

    }
}