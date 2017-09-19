package com.test.nutshell.logintest.data.network

import com.test.nutshell.logintest.data.DataManager
import com.test.nutshell.logintest.data.model.Weather
import io.reactivex.Single
import javax.inject.Inject

class ApiServiceHelper @Inject
constructor(private val apiService: ApiService) {

    fun getWeather(): Single<Weather> = apiService.getWeather(DataManager.DEFAULT_CITY,
            DataManager.DEAFAULT_UNITS, DataManager.API_KEY)
            .map { Weather(weatherDto = it) }
}
