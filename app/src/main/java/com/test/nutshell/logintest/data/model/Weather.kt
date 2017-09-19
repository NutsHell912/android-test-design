package com.test.nutshell.logintest.data.model

import com.test.nutshell.logintest.data.network.WeatherDto

data class Weather(
        val temperature: Double,
        val pressure: Double,
        val windspeed: String
) {
    constructor(weatherDto: WeatherDto): this(
            temperature = weatherDto.main.temp,
            pressure = weatherDto.main.pressure,
            windspeed = weatherDto.wind.speed
    )
}