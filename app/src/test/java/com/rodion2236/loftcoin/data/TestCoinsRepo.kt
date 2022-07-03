package com.rodion2236.loftcoin.data

import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.data.models.currency.Currency
import com.rodion2236.loftcoin.repository.coin.CoinsRepo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class TestCoinsRepo: CoinsRepo {
    val listings = PublishSubject.create<List<Coin>>()

    override fun listings(query: CoinsRepo.Query): Observable<List<Coin>> = listings

    override fun coin(currency: Currency, id: Long): Single<Coin> {
        TODO("Not yet implemented")
    }
}