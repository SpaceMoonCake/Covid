package com.spacemooncake.covid.ui.main

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacemooncake.covid.R
import com.spacemooncake.covid.model.rest_entities.Country
import com.spacemooncake.covid.model.rest_entities.CountryStatus
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreen(countryName: String, countriesInfo: List<Country>, mainViewModel : MainViewModel) {

    val countryInfo = searchCountry(countriesInfo, countryName)

    mainViewModel.getTwoWeekHistory(countryInfo.slug)

    val countryHistory by mainViewModel.countryHistory.collectAsState()
    val isLoading by mainViewModel.isLoading.collectAsState()

    when {
        !isLoading -> printUI(countryHistory, countryInfo)
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun printUI(countryHistory: List<CountryStatus>, countryInfo: Country) {

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.purple_200))
            .wrapContentSize(Alignment.TopCenter)
    ) {

        Card(
            modifier = Modifier
                .wrapContentSize(Alignment.TopCenter),
            shape = RoundedCornerShape(15.dp)
        ) {
            InfoToday(countryInfo = countryInfo)
        }

        Card(
            modifier = Modifier
                .wrapContentSize(Alignment.TopCenter),
            shape = RoundedCornerShape(15.dp)
        ) {
            Column() {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "Date", fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Death", fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Rec", fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Con", fontWeight = FontWeight.Bold
                    )
                }

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(countryHistory) { countyStatus: CountryStatus ->
                        HistoryItemCard(countyStatus)

                    }
                }
            }

        }


    }

}

@Composable
fun HistoryItemCard(countyStatus: CountryStatus) {
    Card(modifier = Modifier.padding(1.dp)) {
        Row(
            modifier = Modifier
                .background(colorResource(id = R.color.gray))
                .height(40.dp)
                .fillMaxWidth()
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            getFormattedDateTime(countyStatus.date)?.let {
                Text(text = it, fontSize = 15.sp)
            }
            Text(text = "${countyStatus.confirmed}")
            Text(text = "${countyStatus.death}")
            Text(text = "${countyStatus.recovered}")
        }

    }

}


@Composable
fun InfoToday(countryInfo: Country) {
    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.purple_200))
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopCenter)
            .padding(5.dp)
    )

    {

        Text(
            text = countryInfo.countryName,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Total confirmed: ${countryInfo.totalConfirmedCovidCase}",
            fontSize = 20.sp,
            color = colorResource(id = R.color.yelloy),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )
        Text(
            text = "Total death: ${countryInfo.totalDeathCase}",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = colorResource(id = R.color.red),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)

        )

        Text(
            text = "Total recovered: ${countryInfo.totalRecoveredCase}",
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = colorResource(id = R.color.green),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
        )

        }
}


fun searchCountry(countries: List<Country>, countryName: String): Country {
    for (country in countries) {
        if (country.countryName == countryName)
            return country
    }
    return countries[0]
}

fun getFormattedDateTime(date: String): String? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        ZonedDateTime.parse(date)
            .format(DateTimeFormatter.ofPattern("dd LLL "))
    } else null
}
