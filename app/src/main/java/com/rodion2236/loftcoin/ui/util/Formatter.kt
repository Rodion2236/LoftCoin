package com.rodion2236.loftcoin.ui.util

interface Formatter<T> {
    fun format(value: T): String
}