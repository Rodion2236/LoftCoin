package com.rodion2236.loftcoin.ui.activity.currency

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftcoin.data.models.currency.Currency
import com.rodion2236.loftcoin.data.models.currency.CurrencyRepo
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val currencyRepo: CurrencyRepo
    ) : ViewModel() {

    fun allCurrencies(): LiveData<List<Currency>> {
        return currencyRepo.availableCurrencies()
    }

    fun updateCurrency(currency: Currency) {
        currencyRepo.updateCurrency(currency)
    }
}