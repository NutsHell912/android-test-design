package com.test.nutshell.logintest.ui.main

import com.test.nutshell.logintest.data.DataManager
import com.test.nutshell.logintest.data.model.Weather
import com.test.nutshell.logintest.data.network.WeatherDto
import com.test.nutshell.logintest.injection.ConfigPersistent
import com.test.nutshell.logintest.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ConfigPersistent
class MainPresenter @Inject
constructor(private val dataManager: DataManager) : BasePresenter<MainMvpView>() {

    private var weatherDisposable = Disposables.disposed()
    private var email = ""
    private var password = ""
    //храним для переворота экрана, в локальном хранилище не сохраняем
    private var weather: Weather? = null
    private var isLoading = false

    override fun attachView(mvpView: MainMvpView) {
        super.attachView(mvpView)
        email = dataManager.getEmail()
        password = dataManager.getPassword()
        render()
    }

    override fun detachView() {
        super.detachView()
        //или создать метод onDestroy, где будем отписываться от сети
        //тогда можно будет в бэкграунде делать запрос
        weatherDisposable.dispose()
        isLoading = false
        dataManager.saveEmail(email)
        dataManager.savePassword(password)
    }

    fun onStartAuth() {
        mvpView?.let {
            if (!dataManager.validateEmail(email)) {
                it.showEmailValidationError()
                return
            }
            if (!dataManager.validatePassword(password)) {
                it.showPasswordValidationError()
                return
            }
            if (!weatherDisposable.isDisposed) {
                weatherDisposable.dispose()
            }
            isLoading = true
            render()
            weatherDisposable = dataManager.getWeather()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { weather, error ->
                        if (weather != null) {
                            this.weather = weather
                        }
                        if (error != null) {
                            mvpView?.showError(error)
                        }
                        isLoading = false
                        render()
                    }

        }
    }

    fun onEmailChange(email: String) {
        this.email = email
    }

    fun onPasswordChanged(password: String) {
        this.password = password
    }

    fun onForgetPasswordClicked() {
        mvpView?.navigateToForgetActivity()
    }

    fun onCreateClicked() {
        mvpView?.navigateToSignUpActivity()
    }

    fun onBackClicked() {
        mvpView?.onBack()
    }

    private fun render() {
        mvpView?.let {
            it.setLoading(isLoading)
            it.renderEmail(email)
            it.renderPassword(password)
            if(weather != null) {
                it.showWeather(weather.toString())
                weather = null
            }
        }
    }

}
