package com.test.nutshell.logintest.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/data/2.5/weather")
    fun getWeather(@Query("q") city: String, @Query("units") units: String, @Query("APPID") appid: String)
            : Single<WeatherDto>

}
