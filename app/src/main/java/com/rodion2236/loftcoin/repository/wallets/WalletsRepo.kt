package com.rodion2236.loftcoin.repository.wallets

import com.rodion2236.loftcoin.data.models.currency.Currency
import com.rodion2236.loftcoin.data.models.wallet.Transaction
import com.rodion2236.loftcoin.data.models.wallet.Wallet
import io.reactivex.Observable

interface WalletsRepo {
    fun wallets(currency: Currency) : Observable<List<Wallet>>
    fun transactions(wallet: Wallet) : Observable<List<Transaction>>
}