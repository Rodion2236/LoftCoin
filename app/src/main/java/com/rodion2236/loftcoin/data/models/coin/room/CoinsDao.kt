package com.rodion2236.loftcoin.data.models.coin.room

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    abstract fun fetchAll(): Observable<List<RoomCoin>>

    @Query("SELECT * from RoomCoin WHERE id=:id")
    fun fetchOne(id: Long) : Single<RoomCoin>

    @Query("SELECT * FROM RoomCoin ORDER BY price DESC")
    abstract fun fetchAllSortByPrice(): Observable<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC")
    abstract fun fetchAllSortByRank(): Observable<List<RoomCoin>>

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    abstract fun coinsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(coins: List<RoomCoin>)
}