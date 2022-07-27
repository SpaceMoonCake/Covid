package com.spacemooncake.covid.model.rest_entities

import com.google.gson.annotations.SerializedName

data class CountryStatus(
    @SerializedName("Country")
    val countryName: String,
    @SerializedName("Confirmed")
    val confirmed: Int,
    @SerializedName("Deaths")
    val death: Int,
    @SerializedName("Recovered")
    val recovered: Int,
    @SerializedName("Date")
    val date: String
)
