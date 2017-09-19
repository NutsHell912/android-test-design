package com.test.nutshell.logintest.data

import com.test.nutshell.logintest.data.db.SharedPreferencesHelper
import com.test.nutshell.logintest.data.model.Weather
import com.test.nutshell.logintest.data.network.ApiServiceHelper
import com.test.nutshell.logintest.data.network.WeatherDto
import io.reactivex.Single
import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataManager
@Inject constructor(private val apiServiceHelper: ApiServiceHelper,
                    private val sharedPreferncesHelper: SharedPreferencesHelper) {

    fun getWeather(): Single<Weather> {
        return apiServiceHelper.getWeather()
    }

    fun validateEmail(email: String): Boolean {
        return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(pswd: String): Boolean {
        if(pswd.length < 8)
            return false
        val patternUpLetter = Pattern.compile(".*[A-Z]+.*")
        val tmp = patternUpLetter.matcher(pswd).matches()
        if(!(patternUpLetter.matcher(pswd).matches()))
            return false
        val patternLowLetter = Pattern.compile(".*[a-z]+.*")
        if(!patternLowLetter.matcher(pswd).matches())
            return false
        val patternDigits = Pattern.compile(".*[0-9]+.*")
        if(!patternDigits.matcher(pswd).matches())
            return false
        return true

    }

    fun savePassword(password: String) {
        sharedPreferncesHelper.password = password
    }

    fun saveEmail(email: String) {
        sharedPreferncesHelper.email = email
    }

    fun getEmail(): String = sharedPreferncesHelper.email

    fun getPassword(): String = sharedPreferncesHelper.password

    companion object {
        val API_KEY = "035c3259e230f54310168910a9268887"
        val DEFAULT_CITY = "Moscow,rus"
        val DEAFAULT_UNITS = "metric"
    }
}