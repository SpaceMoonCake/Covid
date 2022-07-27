package com.spacemooncake.covid.model.database

import androidx.room.RoomDatabase

@androidx.room.Database(
    entities = [
        CountryEntity::class,
        CountryHistoryEntity:: class
    ],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun countryHistoryDao(): CountryHistoryDao
}