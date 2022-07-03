package com.rodion2236.loftcoin.repository.wallets

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.rodion2236.loftcoin.data.models.currency.Currency
import com.rodion2236.loftcoin.data.models.wallet.Transaction
import com.rodion2236.loftcoin.data.models.wallet.Wallet
import com.rodion2236.loftcoin.repository.coin.CoinsRepo
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WalletsRepoImpl @Inject constructor(
    private val coinsRepo: CoinsRepo,
) : WalletsRepo {

    private val firestore = FirebaseFirestore.getInstance()

    companion object {
        private const val wallets_collection = "wallets"
        private const val wallets_coinId = "coinId"
        private const val wallets_balance = "balance"
    }

    override fun wallets(currency: Currency): Observable<List<Wallet>> {
        return Observable.create<QuerySnapshot> { emitter ->
            val registration =
                firestore.collection(wallets_collection).addSnapshotListener { snapshot, e ->
                    if (!emitter.isDisposed) {
                        if (snapshot != null) {
                            emitter.onNext(snapshot)
                        } else {
                            emitter.tryOnError(e?.cause ?: Exception("Something was wrong!"))
                        }
                    }
                }
            emitter.setCancellable { registration.remove() }
        }
            .map { snapshot -> snapshot.documents }
            .switchMapSingle { docs ->
                Observable.fromIterable(docs)
                    .switchMapSingle { doc ->
                        coinsRepo
                            .coin(currency, doc.getLong(wallets_coinId)
                                ?: throw IllegalArgumentException("Expected $wallets_coinId field"))
                            .map { coin ->
                                Wallet(doc.id, coin, doc.getDouble(wallets_balance)
                                    ?: throw IllegalArgumentException("Expected $wallets_balance field"))
                            }
                    }
                    .toList()
            }
    }

    override fun transactions(wallet: Wallet): Observable<List<Transaction>> {
        TODO("Not yet implemented")
    }
}