package com.rodion2236.loftcoin.data.models.coin.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinsDao {

    @Query("SELECT * from RoomCoin")
    fun fetchAll(): LiveData<List<RoomCoin>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(coins: List<RoomCoin>)

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    fun coinsCount() : Int
}