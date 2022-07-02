package com.rodion2236.loftcoin.ui.util

import android.icu.text.NumberFormat
import android.os.Build
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.HashMap

@Singleton
class PriceFormatter @Inject constructor() : Formatter<Double> {
    override fun format(value: Double): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NumberFormat.getCurrencyInstance().format(value)
        } else {
            NumberFormat.getCurrencyInstance().format(value)
        }
    }

    private val LOCALES = HashMap<String, Locale>()

    init {
        LOCALES["RUB"] = Locale("ru", "RU")
        LOCALES["EUR"] = Locale.GERMANY
        LOCALES["USD"] = Locale.US
    }

    fun format(currency: String, value: Double): String {
        val locale = LOCALES[currency]
        return java.text.NumberFormat.getCurrencyInstance(locale).format(value)
    }
}