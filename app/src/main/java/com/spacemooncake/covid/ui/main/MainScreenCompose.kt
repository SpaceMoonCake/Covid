package com.spacemooncake.covid.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.spacemooncake.covid.R
import java.util.*
import kotlin.collections.ArrayList
import androidx.compose.material.IconButton as IconButton1


@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Covid statistic", fontSize = 18.sp, fontWeight = FontWeight.Bold)},
        backgroundColor = colorResource(id = R.color.gray),
        contentColor = colorResource(id = R.color.black),
    )
}


@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = androidx.compose.ui.text.TextStyle(color = White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton1(
                    onClick = {
                        state.value =
                            TextFieldValue("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = RectangleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            cursorColor = White,
            leadingIconColor = White,
            trailingIconColor = White,
            backgroundColor = colorResource(id = R.color.purple_200),
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    SearchView(textState)
}


@Composable
fun CountryListItem(countryName: String, onItemClick: (String) -> Unit) {
    Card(modifier = Modifier.padding(1.dp)) {
        Row(
            modifier = Modifier
                .clickable(onClick = { onItemClick(countryName) })
                .background(colorResource(id = R.color.purple_200))
                .height(50.dp)
                .fillMaxWidth()
                .padding(2.dp)
        ) {
            Text(text = countryName, fontSize = 20.sp, color = Black, fontWeight = FontWeight.Bold)
        }

    }
}

@Composable
fun CountryList(
    navController: NavController,
    state: MutableState<TextFieldValue>,
    countries: ArrayList<String>
) {
    var filteredCountries: java.util.ArrayList<String>
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        val searchedText = state.value.text
        filteredCountries = if (searchedText.isEmpty()) {
            countries
        } else {
            val resultList = java.util.ArrayList<String>()
            for (country in countries) {
                if (country.lowercase(Locale.getDefault())
                        .contains(searchedText.lowercase(Locale.getDefault()))
                ) {
                    resultList.add(country)
                }
            }
            resultList
        }
        items(filteredCountries) { filteredCountry ->
            CountryListItem(
                countryName = filteredCountry,
                onItemClick = { selectedCountry ->
                    navController.navigate("details/$selectedCountry") {
                        popUpTo("main") {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


@Composable
fun MainScreen(navController: NavController, countries: ArrayList<String>) {
    val textState = remember { mutableStateOf(TextFieldValue("")) }
    Column {
        SearchView(textState)
        CountryList(navController = navController, state = textState, countries)
    }
}





