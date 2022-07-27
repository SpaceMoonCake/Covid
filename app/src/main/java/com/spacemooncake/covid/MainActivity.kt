package com.spacemooncake.covid

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.spacemooncake.covid.model.rest_entities.Country
import com.spacemooncake.covid.ui.main.*
import com.spacemooncake.covid.ui.theme.CovidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val mainViewModel = viewModel(modelClass = MainViewModel::class.java)
            mainViewModel.getCountriesName()
            mainViewModel.getCovidData()
            val countriesNames by mainViewModel.countriesName.collectAsState()
            val countriesInfo by mainViewModel.countries.collectAsState()
            CovidTheme {
                Scaffold(
                    topBar = { TopBar() },
                    backgroundColor = colorResource(id = R.color.white)
                ) {
                    Navigation(countriesNames, countriesInfo, mainViewModel)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun Navigation(countries: ArrayList<String>,countriesInfo: List<Country>, mainViewModel : MainViewModel ) {

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "main") {
            composable("main") {
                MainScreen(navController = navController, countries)
            }
            composable(
                "details/{countryName}",
                arguments = listOf(navArgument("countryName") { type = NavType.StringType })
            ) { backStackEntry ->
                backStackEntry.arguments?.getString("countryName")?.let { countryName ->
                    DetailsScreen(countryName = countryName, countriesInfo, mainViewModel)
                }
            }
        }
    }
}



