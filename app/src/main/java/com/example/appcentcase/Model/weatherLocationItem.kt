package com.example.appcentcase.Model


import com.google.gson.annotations.SerializedName

data class weatherLocationItem(
    @SerializedName("distance")
    val distance: Int,
    @SerializedName("latt_long")
    val lattLong: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("woeid")
    val woeid: Int
)