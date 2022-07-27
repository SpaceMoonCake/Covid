package com.spacemooncake.covid.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryHistoryDao {

    @Query("SELECT * FROM CountryHistoryEntity")
    fun all(): List<CountryHistoryEntity>

    @Query("SELECT * FROM CountryHistoryEntity WHERE countryName LIKE :countryName")
    fun getDataByCountryName(countryName: String): List<CountryHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CountryHistoryEntity)
}