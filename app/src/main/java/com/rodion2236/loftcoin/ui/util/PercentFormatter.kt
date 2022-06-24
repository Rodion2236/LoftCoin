package com.rodion2236.loftcoin.ui.util

import java.util.*

class PercentFormatter() : Formatter<Double> {

    override fun format(value: Double): String {
        return String.format(Locale.US, "%.2f%%", value)
    }
}