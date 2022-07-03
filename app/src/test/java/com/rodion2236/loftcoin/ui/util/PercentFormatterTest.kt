package com.rodion2236.loftcoin.ui.util

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class PercentageFormatterTest {

    private lateinit var formatter: PercentFormatter

    @Before
    fun setUp() {
        formatter = PercentFormatter()
    }

    @Test
    fun string_contains_exact_three_fractional_digits() {
        assertEquals(formatter.format(1f.toDouble()), "1.000%")
        assertEquals(formatter.format(1.2345), "1.23%")
        assertEquals(formatter.format(1.2356), "1.24%")
    }
}