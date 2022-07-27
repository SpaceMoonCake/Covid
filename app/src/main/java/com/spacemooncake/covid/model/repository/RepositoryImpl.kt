package com.spacemooncake.covid.model.repository
import com.spacemooncake.covid.model.database.CountryEntity
import com.spacemooncake.covid.model.database.CountryHistoryEntity
import com.spacemooncake.covid.model.database.Database
import com.spacemooncake.covid.model.rest.CovidRepository
import com.spacemooncake.covid.model.rest_entities.Country
import com.spacemooncake.covid.model.rest_entities.CountryName
import com.spacemooncake.covid.model.rest_entities.CountryStatus
import com.spacemooncake.covid.model.rest_entities.CovidData
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val db: Database) : Repository {
    override fun getCovidDataFromServer(): CovidData? {
        return CovidRepository.api.getAllCountries().execute().body()
    }

    override fun getCountriesName(): ArrayList<String>? {
        val countriesName = CovidRepository.api.getListCountries().execute().body()
        return countriesName?.let { getListNameOfCountries(it) }
    }

    override fun getCountryStatusHistory(
        countrySlug: String,
        from: String,
        to: String
    ): List<CountryStatus>? {
        return CovidRepository.api.getHistoryCountry(countrySlug, from, to).execute().body()
    }

    override fun saveCountryEntity(countries: List<Country>) {
        for (country in countries) {
            db.countryDao().insert(convertCountryToEntity(country))
        }
    }

    override fun getCountriesInfoFromDB(): List<Country> {
        return convertCountryEntityToCountry(db.countryDao().all())
    }

    override fun saveCountryHistoryEntity(countryHistory: List<CountryStatus>) {
        for (countryState in countryHistory) {
            db.countryHistoryDao().insert(convertCountryHistoryToEntity(countryState))
        }
    }

    override fun getCountryHistoryFromDB(countryName: String): List<CountryStatus> {
        return convertHistoryEntityToCountryState(
            db.countryHistoryDao().getDataByCountryName(countryName = countryName)
        )
    }

    private fun convertCountryToEntity(country: Country): CountryEntity {
        return CountryEntity(
            id = 0,
            countryName = country.countryName,
            slug = country.slug,
            newConfirmedCovidCase = country.newConfirmedCovidCase,
            totalConfirmedCovidCase = country.totalConfirmedCovidCase,
            newDeathCase = country.newDeathCase,
            totalDeathCase = country.totalDeathCase,
            newRecoveredCase = country.newRecoveredCase,
            totalRecoveredCase = country.totalRecoveredCase,
            date = country.date
        )
    }


    private fun convertCountryEntityToCountry(entityList: List<CountryEntity>): List<Country> {
        return entityList.map {
            Country(
                countryName = it.countryName,
                slug = it.slug,
                newConfirmedCovidCase = it.newConfirmedCovidCase,
                totalConfirmedCovidCase = it.totalConfirmedCovidCase,
                newDeathCase = it.newDeathCase,
                totalDeathCase = it.totalDeathCase,
                newRecoveredCase = it.newRecoveredCase,
                totalRecoveredCase = it.totalRecoveredCase,
                date = it.date
            )
        }
    }



    private fun convertCountryHistoryToEntity(countryState: CountryStatus): CountryHistoryEntity {
        return CountryHistoryEntity(
            id = 0,
            countryName = countryState.countryName,
            confirmed = countryState.confirmed,
            death = countryState.death,
            recovered = countryState.recovered,
            date = countryState.date
        )
    }


    private fun convertHistoryEntityToCountryState(entityList: List<CountryHistoryEntity>): List<CountryStatus> {
        return entityList.map {
            CountryStatus(
                countryName = it.countryName,
                confirmed = it.confirmed,
                death = it.death,
                recovered = it.recovered,
                date = it.date
            )
        }
    }

    private fun getListNameOfCountries(countries: ArrayList<CountryName>): ArrayList<String> {
        val array: ArrayList<String> = ArrayList()
        for (country in countries) {
            country.countryName.let { array.add(it) }
        }
        return array
    }

}