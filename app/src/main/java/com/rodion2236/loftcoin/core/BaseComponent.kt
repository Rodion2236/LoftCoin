package com.rodion2236.loftcoin.core

import android.content.Context
import com.rodion2236.loftcoin.repository.CoinsRepo

interface BaseComponent {
    fun context() : Context
    fun coinsRepo() : CoinsRepo
}