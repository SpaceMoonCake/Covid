package com.spacemooncake.covid.model.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CovidRepository {
    val api: CovidAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(CovidAPI::class.java)
    }
}