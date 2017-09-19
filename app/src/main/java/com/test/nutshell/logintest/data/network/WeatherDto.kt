package com.test.nutshell.logintest.data.network

import com.google.gson.annotations.SerializedName

data class WeatherDto(
        @SerializedName("main")
        val main: MainDto,
        @SerializedName("wind")
        val wind: Wind
)

data class MainDto(
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("pressure")
        val pressure: Double
)

data class Wind(
        @SerializedName("speed")
        val speed: String,
        @SerializedName("deg")
        val deg: String
)