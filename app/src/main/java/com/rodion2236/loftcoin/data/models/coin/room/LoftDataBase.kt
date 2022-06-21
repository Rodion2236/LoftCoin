package com.rodion2236.loftcoin.data.models.coin.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RoomCoin::class], version = 1
)
abstract class LoftCoinDatabase : RoomDatabase() {

    abstract fun coins(): CoinsDao
}