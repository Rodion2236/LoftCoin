package com.rodion2236.loftcoin.data.models.currency

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.rodion2236.loftcoin.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepoImpl @Inject constructor(
     context: Context
) : CurrencyRepo {
    private var prefs: SharedPreferences
    private val availableCurrencies: MutableMap<String, Currency> = HashMap()

    init {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)

            availableCurrencies["USD"] = Currency.create("$", "USD", context.getString(R.string.usd))
            availableCurrencies["EUR"] = Currency.create("E", "EUR", context.getString(R.string.eur))
            availableCurrencies["RUB"] = Currency.create("R", "RUB", context.getString(R.string.rub))

    }

    override fun availableCurrencies(): LiveData<List<Currency>> {
        val liveData = MutableLiveData<List<Currency>>()
        liveData.value = ArrayList(availableCurrencies.values)
        return liveData
    }

    override fun currency(): Observable<Currency?> {
        return Observable.create<Currency> { emitter: ObservableEmitter<Currency?> ->
            val listener =
                SharedPreferences.OnSharedPreferenceChangeListener { prefs: SharedPreferences, key: String ->
                    if (!emitter.isDisposed && KEY_CURRENCY == key) {
                        emitter.onNext(availableCurrencies[prefs.getString(key, "USD")]!!)
                    }
                }
            prefs.registerOnSharedPreferenceChangeListener(listener)
            emitter.setCancellable { prefs.unregisterOnSharedPreferenceChangeListener(listener) }
            emitter.onNext(availableCurrencies[prefs.getString(KEY_CURRENCY, "USD")]!!)
        }
    }

    override fun updateCurrency(currency: Currency) {
        prefs.edit().putString(KEY_CURRENCY, currency.code()).apply()
    }

    companion object {
        private const val KEY_CURRENCY = "currency"
    }


}