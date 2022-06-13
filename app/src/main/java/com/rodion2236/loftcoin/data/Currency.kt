package com.rodion2236.loftcoin.data

abstract class Currency {
    abstract fun symbol(): String?
    abstract fun code(): String?
    abstract fun name(): String?

    companion object {
        fun create(symbol: String?, code: String?, name: String?): Currency {
            return create(symbol, code, name)
        }
    }
}