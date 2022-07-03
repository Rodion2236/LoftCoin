package com.rodion2236.loftcoin.ui.activity.wallet

import androidx.lifecycle.ViewModel
import com.rodion2236.loftcoin.data.models.wallet.Wallet
import com.rodion2236.loftcoin.repository.currency.CurrencyRepo
import com.rodion2236.loftcoin.repository.wallets.WalletsRepo
import com.rodion2236.loftcoin.ui.util.RxSchedulers
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

class WalletsViewModel @Inject constructor(
    walletsRepo: WalletsRepo,
    currencyRepo: CurrencyRepo,
    private val schedulers: RxSchedulers
) : ViewModel() {
    private val wallets = currencyRepo.currency()
        .switchMap {
            walletsRepo.wallets(it)
        }.doOnEach {
            Timber.d(it.toString())
        }

    fun getWallets(): Observable<List<Wallet>> = wallets.observeOn(schedulers.main())
}