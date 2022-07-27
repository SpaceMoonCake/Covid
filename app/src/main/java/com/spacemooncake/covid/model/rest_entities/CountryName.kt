package com.spacemooncake.covid.model.rest_entities

import com.google.gson.annotations.SerializedName

data class CountryName(
    @SerializedName("Country")
    val countryName: String
)
