package com.spacemooncake.covid.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryHistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val countryName: String,
    val confirmed: Int,
    val death: Int,
    val recovered: Int,
    val date: String)