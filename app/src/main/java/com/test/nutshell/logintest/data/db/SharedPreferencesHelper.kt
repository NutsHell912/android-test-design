package com.test.nutshell.logintest.data.db

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesHelper @Inject
internal constructor(private val preferences: SharedPreferences) {
    var email: String
        get() = preferences.getString(EMAIL, "")
        set(email) {
            val editor = preferences.edit()
            editor.putString(EMAIL, email)
            editor.commit()
        }

    var password: String
        get() = preferences.getString(PASSWORD, "")
        set(password) {
            val editor = preferences.edit()
            editor.putString(PASSWORD, password)
            editor.commit()
        }

    companion object {
        private val EMAIL = "email"
        private val PASSWORD = "password"

    }
}
