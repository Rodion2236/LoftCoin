package com.rodion2236.loftcoin.repository.coin

import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.api.CmcApi
import com.rodion2236.loftcoin.data.models.listings.Listings
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.coin.room.LoftCoinDatabase
import com.rodion2236.loftcoin.data.models.coin.room.RoomCoin
import com.rodion2236.loftcoin.data.models.currency.Currency
import com.rodion2236.loftcoin.ui.util.RxSchedulers
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CmcCoinsRepo @Inject constructor(
    private val api: CmcApi,
    private val db: LoftCoinDatabase,
    private val schedulers: RxSchedulers
) : CoinsRepo {

    override fun listings(query: CoinsRepo.Query): Observable<List<Coin>> {
        return Observable.fromCallable { query.forceRefresh!! || db.coins().coinsCount() == 0 }
            .switchMap { f -> if (f) api.listings(query.currency!!) else Observable.empty() }
            .map { listings -> mapToRoomCoin(listings) }
            .doOnNext { db.coins().insert(it) }
            .switchMap { fetchFromDb(query) }
            .switchIfEmpty { fetchFromDb(query) }
            .subscribeOn(schedulers.io())
            .map { it }
    }

    private fun mapToRoomCoin(coins: Listings): List<RoomCoin> =
        coins.data.map { coinToRoomCoin(it) }

    private fun coinToRoomCoin(coin: Coin): RoomCoin =
        RoomCoin(
            coin.id,
            coin.name,
            coin.symbol,
            coin.rank,
            coin.price,
            coin.change24h,
            coin.currencyCode
        )

    override fun coin(currency: Currency, id: Long): Single<Coin> {
        return listings(CoinsRepo.Query.currency(currency.code).forceRefresh(false).build())
            .switchMapSingle { db.coins().fetchOne(id) }
            .firstOrError()
            .map { it }
    }

    private fun fetchFromDb(query: CoinsRepo.Query): Observable<List<RoomCoin>> =
        if (query.sortBy == SortBy.RANK) {
            db.coins().fetchAllSortByRank()
        } else {
            db.coins().fetchAllSortByPrice()
        }
}