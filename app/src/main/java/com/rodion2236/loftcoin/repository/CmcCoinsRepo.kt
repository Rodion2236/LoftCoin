package com.rodion2236.loftcoin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.rodion2236.loftcoin.data.api.CmcApi
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.CoinQuery
import com.rodion2236.loftcoin.data.models.coin.room.LoftCoinDatabase
import com.rodion2236.loftcoin.data.models.coin.room.RoomCoin
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CmcCoinsRepo @Inject constructor(
    private val cmcApi: CmcApi,
    private val db: LoftCoinDatabase,
    private val executor: ExecutorService
) : CoinsRepo {

    override fun listings(currency: String): List<Coin> {
        val result = cmcApi.listings(currency).execute()
        if (result.isSuccessful) {
            return result.body()?.data ?:
            throw IllegalStateException("")
        } else {
            throw IOException(result.errorBody()?.string() ?: "")
        }
    }

    override fun listings(query: CoinQuery): LiveData<List<Coin>> {
        val refresh = MutableLiveData<Boolean>()
        executor.submit {
            refresh.postValue(query.forceUpdate || db.coins().coinsCount() == 0)
        }

        return Transformations.switchMap(refresh) {
            if (refresh.value == true) {
                fetchFromNetwork(query)
            } else {
                fetchFromDb(query)
            }
        }
    }

    private fun fetchFromDb(query: CoinQuery): LiveData<List<Coin>> {
        return Transformations.map(db.coins().fetchAll()) { it }
    }

    private fun fetchFromNetwork(query: CoinQuery): LiveData<List<Coin>> {
        val liveData = MutableLiveData<List<Coin>>()
        executor.submit {
            try {
                val result = cmcApi.listings(query.currency).execute()
                if (result.isSuccessful) {
                    val items = result.body()
                    if (items?.data != null) {
                        val coins = items.data
                        saveCoinsIntoDb(coins)
                        liveData.postValue(coins)
                    } else {
                        throw IllegalStateException("")
                    }
                } else {
                    val error = result.errorBody()
                    throw IOException(error?.string() ?: "")
                }
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
        return liveData
    }

    private fun saveCoinsIntoDb(coin: List<Coin>) {
        val roomCoin = coin.map { RoomCoin(
            it.id,
            it.name,
            it.symbol,
            it.rank,
            it.price,
            it.change24h
        ) }
        db.coins().insert(roomCoin)
    }
}