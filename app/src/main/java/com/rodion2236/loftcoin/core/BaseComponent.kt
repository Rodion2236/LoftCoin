package com.rodion2236.loftcoin.core

import android.content.Context
import com.rodion2236.loftcoin.repository.currency.CurrencyRepo
import com.rodion2236.loftcoin.repository.coin.CoinsRepo
import com.rodion2236.loftcoin.repository.wallets.WalletsRepo
import com.rodion2236.loftcoin.ui.util.Notifier
import com.rodion2236.loftcoin.ui.util.RxSchedulers

interface BaseComponent {
    fun context() : Context
    fun coinsRepo() : CoinsRepo
    fun currencyRepo() : CurrencyRepo
    fun walletsRepo(): WalletsRepo
    fun schedulers(): RxSchedulers
    fun notifier(): Notifier
}