package com.rodion2236.loftcoin.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.CoinQuery

interface CoinsRepo {

    @WorkerThread
    fun listings(currency: String) : List<Coin>
    fun listings(query: CoinQuery): LiveData<List<Coin>>
}