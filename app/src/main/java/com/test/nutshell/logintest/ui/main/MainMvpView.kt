package com.test.nutshell.logintest.ui.main

import com.test.nutshell.logintest.ui.base.MvpView

interface MainMvpView : MvpView {

    fun renderPassword(password: String)
    fun renderEmail(email: String)

    fun showError(e: Throwable)
    fun showEmailValidationError()
    fun showPasswordValidationError()
    fun showWrongEmailOrPassword()
    fun onBack()
    fun showWeather(weather: String)
    fun setLoading(isLoading: Boolean)

}
