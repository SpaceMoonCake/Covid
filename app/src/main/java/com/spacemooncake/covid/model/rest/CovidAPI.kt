package com.spacemooncake.covid.model.rest

import com.spacemooncake.covid.model.rest_entities.CountryName
import com.spacemooncake.covid.model.rest_entities.CountryStatus
import com.spacemooncake.covid.model.rest_entities.CovidData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CovidAPI {
    @GET("summary")
    fun getAllCountries(): Call<CovidData>

    @GET("countries")
    fun getListCountries(): Call<ArrayList<CountryName>>

    @GET("country/{getCountry}")
    fun getHistoryCountry(
        @Path("getCountry") countrySlug : String,
        @Query ("from") from: String,
        @Query("to") to: String
    ) : Call<List<CountryStatus>>

}