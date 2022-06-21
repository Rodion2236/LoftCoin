package com.rodion2236.loftcoin.data.models.currency

import androidx.lifecycle.LiveData
import io.reactivex.Observable

interface CurrencyRepo {

    fun availableCurrencies(): LiveData<List<Currency>>
    fun currency(): Observable<Currency?>
    fun updateCurrency(currency: Currency)
}