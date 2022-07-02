package com.rodion2236.loftcoin.ui.activity.rates

import androidx.lifecycle.ViewModel
import com.rodion2236.loftcoin.data.SortBy
import com.rodion2236.loftcoin.data.models.coin.Coin
import com.rodion2236.loftcoin.repository.currency.CurrencyRepo
import com.rodion2236.loftcoin.repository.coin.CoinsRepo
import com.rodion2236.loftcoin.repository.coin.CoinsRepo.Query.Builder
import com.rodion2236.loftcoin.ui.util.RxSchedulers
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

class RatesViewModel @Inject constructor(
    private val coinsRepo: CoinsRepo,
    private val currencyRepo: CurrencyRepo,
    private val schedulers: RxSchedulers
) : ViewModel() {

    private val isRefreshing: Subject<Boolean> = BehaviorSubject.create()
    private val pullToRefresh = BehaviorSubject.createDefault(Void.TYPE)
    private val sortBy = BehaviorSubject.createDefault(SortBy.RANK)
    private val forceRefresh = AtomicBoolean()
    private var sortingIndex = 1

    private val coins: Observable<List<Coin>> = pullToRefresh.observeOn(schedulers.main())
        .map { Builder }
        .switchMap { qb -> currencyRepo.currency()
            .map { c -> qb.currency(c.code) }
        }
        .doOnNext { forceRefresh.set(true) }
        .doOnNext { isRefreshing.onNext(true) }
        .switchMap {
                qb -> sortBy.map { s -> qb.sortBy(s) } }
        .map { qb -> qb.forceRefresh(forceRefresh.getAndSet(false)) }
        .map { CoinsRepo.Query.build() }
        .switchMap { query  -> coinsRepo.listings(query) }
        .doOnEach { isRefreshing.onNext(false)
        }

    fun coins(): Observable<List<Coin>> {
        return coins.observeOn(schedulers.main())
    }

    fun isRefreshing(): Observable<Boolean> {
        return isRefreshing.observeOn(schedulers.main())
    }

    fun refresh() {
        pullToRefresh.onNext(Void.TYPE)
    }

    fun switchSortingOrder() {
        sortBy.onNext(SortBy.values()[sortingIndex++ % SortBy.values().size])
    }

}