package com.spacemooncake.covid.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spacemooncake.covid.model.repository.Repository
import com.spacemooncake.covid.model.rest_entities.Country
import com.spacemooncake.covid.model.rest_entities.CountryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
    ) : ViewModel() {

    private val _countries = MutableStateFlow(emptyList<Country>())
    val countries: StateFlow<List<Country>> get() = _countries

    private val _countriesName = MutableStateFlow(ArrayList<String>())
    val countriesName: StateFlow<ArrayList<String>> get() = _countriesName

    private val localCountryHistory = MutableStateFlow(emptyList<CountryStatus>())
    val countryHistory: StateFlow<List<CountryStatus>> get() = localCountryHistory

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading


    @RequiresApi(Build.VERSION_CODES.O)
    fun getTwoWeekHistory(countrySlug: String){
        viewModelScope.launch (Dispatchers.IO){
            _isLoading.value = true
            val dateTo = "${LocalDate.now()}T00:00:00Z"
            val dateFrom ="${LocalDate.now().minusDays(14)}T00:00:00Z"
            localCountryHistory.value =
                repository.getCountryStatusHistory(countrySlug, dateFrom, dateTo)!!
            _isLoading.value = false
        }

        saveHistoryInDB()
    }

    private fun saveHistoryInDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveCountryHistoryEntity(localCountryHistory.value)
        }
    }

    fun getCountriesName(){
        viewModelScope.launch (Dispatchers.IO){
            _countriesName.value = repository.getCountriesName()!!
        }
    }

    fun getCovidData(){
        viewModelScope.launch (Dispatchers.IO){
            _countries.value = repository.getCovidDataFromServer()?.countries!!
        }
        saveDataInDB()
    }

    private fun saveDataInDB(){
        viewModelScope.launch (Dispatchers.IO){
            repository.saveCountryEntity(_countries.value)
        }
    }

}