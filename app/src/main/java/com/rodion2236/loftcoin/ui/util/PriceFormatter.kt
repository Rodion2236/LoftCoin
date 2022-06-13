package com.rodion2236.loftcoin.ui.util

import android.icu.text.NumberFormat
import android.os.Build


class PriceFormatter() : Formatter<Double> {

    override fun format(value: Double): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return NumberFormat.getCurrencyInstance().format(value)
        } else {
            return java.text.NumberFormat.getCurrencyInstance().format(value)
        }
    }
}