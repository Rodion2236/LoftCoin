package com.rodion2236.loftcoin.ui.activity.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodion2236.loftcoin.data.models.Coin
import com.rodion2236.loftcoin.data.repository.CmcCoinsRepo
import java.util.concurrent.Executors
import java.util.concurrent.Future

class RatesViewModel: ViewModel() {

    private val cmcCoinsRepo = CmcCoinsRepo()

    private val _coins = MutableLiveData<List<Coin>>()
    val coins: LiveData<List<Coin>> = _coins

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val executor = Executors.newSingleThreadExecutor()
    private var future: Future<*>? = null

    init {
        refresh()
    }

    fun refresh() {
        _isLoading.postValue(true)
        future = executor.submit {
            try {
                val listings = cmcCoinsRepo.listings("USD")
                _coins.postValue(listings)
            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    override fun onCleared() {
        future?.cancel(true)
        super.onCleared()
    }
}