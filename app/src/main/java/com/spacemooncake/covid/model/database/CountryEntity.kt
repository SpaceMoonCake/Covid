package com.spacemooncake.covid.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val countryName: String,
    val slug: String,
    val newConfirmedCovidCase: Int,
    val totalConfirmedCovidCase: Int,
    val newDeathCase: Int,
    val totalDeathCase: Int,
    val newRecoveredCase: Int,
    val totalRecoveredCase: Int,
    val date: String
)

