package com.rodion2236.loftcoin.ui.util

import com.rodion2236.loftcoin.data.models.wallet.Wallet
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BalanceFormatter @Inject constructor() : Formatter<Wallet> {

    override fun format(value: Wallet): String {
        val format = NumberFormat.getCurrencyInstance() as DecimalFormat
        val symbol = format.decimalFormatSymbols
        symbol.currencySymbol = value.getCoin().symbol
        format.decimalFormatSymbols = symbol
        return format.format(value.getBalance())
    }
}