package com.example.appcentcase.Network

import com.example.appcentcase.Model.cityWeather
import com.example.appcentcase.Model.weatherLocation
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherShowService {

    @GET("location/search/?")
    suspend fun getCitiesByLocation(@Query("lattlong") location:String?):weatherLocation

    @GET("location/search/?")
    suspend fun getCitiesBySearch(@Query("query") search:String):weatherLocation

    @GET("location/{woeid}/")
    suspend fun getWeatherCity(@Path("woeid") woeid:Int):cityWeather

}