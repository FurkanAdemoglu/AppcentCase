package com.example.appcentcase.Network

import com.example.appcentcase.Model.weatherLocation
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherShowService {

    @GET("location/search/")
    suspend fun getCitiesByLocation(@Query("lattlong") location:String):List<weatherLocation>
}