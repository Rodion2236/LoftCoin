package com.rodion2236.loftcoin.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.rodion2236.loftcoin.R
import io.reactivex.Observable

class CurrencyRepoImpl : CurrencyRepo {

    private lateinit var context: Context

    override fun availableCurrencies(): LiveData<List<Currency>> {
        TODO("Not yet implemented")
    }

    override fun currency(): Observable<Currency?> {
        TODO("Not yet implemented")
    }

    override fun updateCurrency(currency: Currency) {
        TODO("Not yet implemented")
    }

    private inner class CurrencyLiveData : LiveData<Currency>() {

        override fun onActive() {
            val currencies: MutableList<Currency> = ArrayList()
            currencies.add(Currency.create("$", "USD", context.getString(R.string.usd)))
            currencies.add(Currency.create("E", "EUR", context.getString(R.string.eur)))
            currencies.add(Currency.create("R", "RUB", context.getString(R.string.rub)))
        }

        override fun onInactive() {
        }
    }
}