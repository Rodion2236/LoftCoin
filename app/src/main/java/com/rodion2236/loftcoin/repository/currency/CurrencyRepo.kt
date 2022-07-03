package com.rodion2236.loftcoin.repository.currency

import androidx.lifecycle.LiveData
import com.rodion2236.loftcoin.data.models.currency.Currency
import io.reactivex.Observable

interface CurrencyRepo {
    fun availableCurrencies(): LiveData<List<Currency>>
    fun currency(): Observable<Currency>
    fun updateCurrency(currency: Currency)
}