package com.spacemooncake.covid.model.repository

import com.spacemooncake.covid.model.rest_entities.Country
import com.spacemooncake.covid.model.rest_entities.CountryStatus
import com.spacemooncake.covid.model.rest_entities.CovidData

interface Repository {
    fun getCovidDataFromServer(): CovidData?
    fun getCountriesName(): ArrayList<String>?
    fun getCountryStatusHistory(countrySlug: String, from: String, to: String): List<CountryStatus>?

    fun saveCountryEntity(countries: List<Country>)
    fun getCountriesInfoFromDB(): List<Country>

    fun saveCountryHistoryEntity(countryHistory: List<CountryStatus>)
    fun getCountryHistoryFromDB(countryName: String) : List<CountryStatus>
}