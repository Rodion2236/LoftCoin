package com.rodion2236.loftcoin.data.models.coin.room

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CoinsDao {

    @Query("SELECT * FROM RoomCoin")
    abstract fun fetchAll(): LiveData<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY price DESC")
    abstract fun fetchAllSortByPrice(): LiveData<List<RoomCoin>>

    @Query("SELECT * FROM RoomCoin ORDER BY rank ASC")
    abstract fun fetchAllSortByRank(): LiveData<List<RoomCoin>>

    @WorkerThread
    @Query("SELECT COUNT(id) FROM RoomCoin")
    abstract fun coinsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(coins: List<RoomCoin>)
}