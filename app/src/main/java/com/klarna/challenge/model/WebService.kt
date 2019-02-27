package com.klarna.challenge.model

import com.klarna.challenge.model.data.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {

    @GET("{latitude},{longitude}")
    fun getWeatherInfo(
        @Path("latitude") latitude: Double,
        @Path("longitude") longitude: Double
    ): Call<Weather>

}