package com.spacemooncake.covid.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryDao {
    @Query("SELECT * FROM CountryEntity")
    fun all(): List<CountryEntity>

    @Query("SELECT * FROM CountryEntity WHERE countryName LIKE :countryName")
    fun getDataByCountryName(countryName: String): List<CountryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: CountryEntity)

}