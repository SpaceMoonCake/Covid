package com.spacemooncake.covid.model.rest_entities

import com.google.gson.annotations.SerializedName

data class CovidData(
    @SerializedName("Countries")
    val countries: List<Country>
)
