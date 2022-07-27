package com.spacemooncake.covid.model.rest_entities

import com.google.gson.annotations.SerializedName

 class Country(
    @SerializedName("Country")
    val countryName: String,
    @SerializedName("Slug")
    val slug: String,
    @SerializedName("NewConfirmed")
    val newConfirmedCovidCase: Int,
    @SerializedName("TotalConfirmed")
    val totalConfirmedCovidCase: Int,
    @SerializedName("NewDeaths")
    val newDeathCase: Int,
    @SerializedName("TotalDeaths")
    val totalDeathCase: Int,
    @SerializedName("NewRecovered")
    val newRecoveredCase: Int,
    @SerializedName("TotalRecovered")
    val totalRecoveredCase: Int,
    @SerializedName("Date")
    val date: String
)
