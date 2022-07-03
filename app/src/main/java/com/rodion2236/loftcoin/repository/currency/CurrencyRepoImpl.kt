package com.rodion2236.loftcoin.repository.currency

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.data.models.currency.Currency
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepoImpl @Inject constructor(context: Context) : CurrencyRepo {
    private val availableCurrencies: MutableMap<String?, Currency> = HashMap()
    private val prefs: SharedPreferences

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
        availableCurrencies["USD"] = Currency("$", "USD", context.getString(R.string.usd))
        availableCurrencies["EUR"] = Currency("E", "EUR", context.getString(R.string.eur))
        availableCurrencies["RUB"] = Currency("R", "RUB", context.getString(R.string.rub))
    }

    companion object {
        private const val KEY_CURRENCY = "currency"
    }


    override fun availableCurrencies(): LiveData<List<Currency>> {
        val liveData = MutableLiveData<List<Currency>>()
        liveData.value = ArrayList(availableCurrencies.values)
        return liveData
    }

    override fun currency(): Observable<Currency> {
        return Observable.create { emitter ->
            val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
                if (!emitter.isDisposed) {
                    availableCurrencies[prefs.getString(key, "USD")]
                }
            }
            prefs.registerOnSharedPreferenceChangeListener(listener)
            emitter.setCancellable {
                prefs.unregisterOnSharedPreferenceChangeListener(listener)
            }
            emitter.onNext(availableCurrencies[prefs.getString(KEY_CURRENCY, "USD")]!!)
        }
    }

    override fun updateCurrency(currency: Currency) {
        prefs.edit().putString(KEY_CURRENCY, currency.code).apply()
    }
}