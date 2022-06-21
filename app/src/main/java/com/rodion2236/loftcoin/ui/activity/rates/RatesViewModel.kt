package com.rodion2236.loftcoin.ui.activity.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.CoinQuery
import com.rodion2236.loftcoin.data.models.currency.CurrencyRepo
import com.rodion2236.loftcoin.repository.CoinsRepo
import javax.inject.Inject

class RatesViewModel @Inject constructor(currencyRepo: CurrencyRepo, coinsRepo: CoinsRepo, coinQuery: CoinQuery): ViewModel() {

    private val sortBy = MutableLiveData(SortBy.RANK)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val forceRefresh = MutableLiveData(false)

    val coins: LiveData<List<Coin>>

    fun refresh() {
        forceRefresh.postValue(true)
    }

    init {
        refresh()

        val query = Transformations.map(forceRefresh) {
            _isLoading.postValue(true)
            CoinQuery("USD", it)
        }
        val coins = Transformations.switchMap(query) {
            coinsRepo.listings(it)
        }
        this.coins = Transformations.map(coins) {
            _isLoading.postValue(false)
            it
        }
    }
}