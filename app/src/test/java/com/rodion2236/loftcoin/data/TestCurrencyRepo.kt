package com.rodion2236.loftcoin.data

import android.content.Context
import android.content.SharedPreferences
import com.rodion2236.loftcoin.repository.currency.CurrencyRepoImpl

class TestCurrencyRepo(
    context: Context,
    sharedPreferences: SharedPreferences,
) : CurrencyRepoImpl(
    context,
    sharedPreferences
)