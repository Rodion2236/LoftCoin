package com.rodion2236.loftcoin.ui.activity.rates

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rodion2236.loftcoin.data.FakeCoin
import com.rodion2236.loftcoin.data.TestCoinsRepo
import com.rodion2236.loftcoin.data.TestCurrencyRepo
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RatesViewModelTest {

    private lateinit var viewModel: RatesViewModel
    private lateinit var coinsRepo: TestCoinsRepo
    private lateinit var currencyRepo: TestCurrencyRepo

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val sharePreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
        coinsRepo = TestCoinsRepo()
        currencyRepo = TestCurrencyRepo(context, sharePreferences)
        viewModel = RatesViewModel(coinsRepo, currencyRepo)
    }

    @Test
    fun coins() {
        val coinTests = viewModel.coins().test()
        viewModel.isRefreshing().test().assertValue(true)
        val coins = listOf(FakeCoin(), FakeCoin())
        coinsRepo.listings.onNext(coins)
        viewModel.isRefreshing().test().assertValue(false)
        coinTests.assertValue(coins)
    }

    @After
    fun tearDown() {
    }
}