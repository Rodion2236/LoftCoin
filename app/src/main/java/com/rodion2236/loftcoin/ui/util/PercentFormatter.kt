package com.rodion2236.loftcoin.ui.util

import java.util.*
import javax.inject.Inject

class PercentFormatter @Inject constructor() : Formatter<Double> {

    override fun format(value: Double): String {
        return String.format(Locale.US, "%.2f%%", value)
    }
}