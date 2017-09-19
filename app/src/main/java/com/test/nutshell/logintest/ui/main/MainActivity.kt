package com.test.nutshell.logintest.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.TextAppearanceSpan
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.test.nutshell.logintest.R
import com.test.nutshell.logintest.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity(), MainMvpView {

    @Inject
    lateinit var presenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent().inject(this)
        setContentView(R.layout.activity_main)
        setupInstance()
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    private fun setupInstance() {
        passwordField.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginButton.callOnClick()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        loginButton.setOnClickListener {
            presenter.onStartAuth()
        }
        emailField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onEmailChange(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        passwordField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                presenter.onPasswordChanged(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        passwordField.setOnTouchListener { view, event ->
            val DRAWABLE_RIGHT = 2
            var isHandled = false
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (passwordField.right - passwordField
                        .compoundDrawables[DRAWABLE_RIGHT].bounds.width())) {
                    presenter.onForgetPasswordClicked()
                    isHandled = true
                }
            }
            isHandled

        }
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        swipeRefreshLayout.isEnabled = false

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_create -> {
                presenter.onCreateClicked()
                return true
            }
            android.R.id.home -> {
                presenter.onBackClicked()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.action_create)
        val s = SpannableString(item.title)
        s.setSpan(TextAppearanceSpan(this, R.style.TextStyle4), 0, s.length, 0)
        item.title = s
        return true
    }

    override fun onBackPressed() {
        presenter.onBackClicked()
    }

    override fun renderPassword(password: String) {
        passwordField.setText(password)
    }

    override fun renderEmail(email: String) {
        emailField.setText(email)
    }

    override fun onBack() {
        finish()
    }

    override fun setLoading(isLoading: Boolean) {
        swipeRefreshLayout.isRefreshing = isLoading
        emailField.isEnabled =  !isLoading
        passwordField.isEnabled =  !isLoading
        loginButton.isEnabled =  !isLoading

    }

    override fun showError(e: Throwable) {
        Snackbar.make(swipeRefreshLayout, e.message.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun showEmailValidationError() {
        emailField.error = resources.getString(R.string.wrong_email)
    }

    override fun showPasswordValidationError() {
        passwordField.error = resources.getString(R.string.wrong_password)
    }

    override fun showWrongEmailOrPassword() {
        passwordField.error = resources.getString(R.string.wrong_username_and_password)
    }

    override fun showWeather(weather: String) {
        Snackbar.make(swipeRefreshLayout,weather, Snackbar.LENGTH_LONG).show()
    }
}
