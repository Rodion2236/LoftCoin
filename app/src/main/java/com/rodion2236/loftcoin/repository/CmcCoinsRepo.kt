package com.rodion2236.loftcoin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.api.CmcApi
import com.rodion2236.loftcoin.data.models.Listings
import com.rodion2236.loftcoin.data.models.coin.CmcCoin
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.room.LoftCoinDatabase
import com.rodion2236.loftcoin.data.models.coin.room.RoomCoin
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CmcCoinsRepo @Inject constructor(
    private val api: CmcApi,
    private val db: LoftCoinDatabase,
    private val executor: ExecutorService
) : CoinsRepo {

    override fun listings(query: CoinsRepo.Query): LiveData<List<Coin>> {
        fetchFromNetworkIfNecessary(query)
        return try {
            fetchFromDb(query)
        } catch (e: Exception) {
            fetchFromDb(query)
        }
    }

    private fun fetchFromDb(query: CoinsRepo.Query): LiveData<List<Coin>> {
        val coins: LiveData<List<RoomCoin>> = if (query.sortBy == SortBy.PRICE) {
            db.coins().fetchAllSortByPrice()
        } else {
            db.coins().fetchAllSortByRank()
        }
        return Transformations.map<List<RoomCoin>, List<Coin>>(coins) { roomCoins ->
            roomCoins.map {
                Coin(
                    id = it.id,
                    name = it.name,
                    symbol = it.symbol,
                    rank = it.rank,
                    price = it.price,
                    change24h = it.change24h,
                    currencyCode = it.currencyCode
                )
            }
        }
    }

    private fun fetchFromNetworkIfNecessary(query: CoinsRepo.Query) {
        executor.submit {
            if (query.forceUpdate || db.coins().coinsCount() == 0) {
                try {
                    val response: Response<Listings> =
                        api.listings(query.currency).execute()
                    if (response.isSuccessful) {
                        val listings = response.body()
                        if (listings != null) {
                            saveCoinsIntoDb(query, listings.data)
                        }
                    } else {
                        val responseBody = response.errorBody()
                        if (responseBody != null) {
                            throw IOException(responseBody.string())
                        }
                    }
                } catch (e: IOException) {
                    Timber.e(e)
                }
            }
        }
    }

    private fun saveCoinsIntoDb(query: CoinsRepo.Query, coins: List<CmcCoin>) {
        val roomCoins: MutableList<RoomCoin> = java.util.ArrayList<RoomCoin>(coins.size)
        for (coin in coins) {
            roomCoins.add(
                RoomCoin(
                    name = coin.name,
                    symbol = coin.symbol,
                    rank = coin.rank,
                    price = coin.quote.values.iterator().next().price,
                    change24h = coin.quote.values.iterator().next().change24h,
                    currencyCode = query.currency,
                    id = coin.id
                )
            )
        }
        db.coins().insert(roomCoins)
    }

}