package com.rodion2236.loftcoin.data.repository

import androidx.annotation.WorkerThread
import com.rodion2236.loftcoin.data.models.Coin

interface CoinsRepo {

    @WorkerThread
    fun listings(currency: String) : List<Coin>
}