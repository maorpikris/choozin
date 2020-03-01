package com.choozin.infra

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.managers.AuthenticationManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    private val authManager: AuthenticationManager = AuthenticationManager.getInstance()
    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authManager.clearToken()
        if (authManager.isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        } else {
            setContentView(R.layout.activity_login)
        }
    }

    fun loginButtonClicked(view: View) {
        if (checkValidation(email_login.text.toString(), password_login.text.toString())) {
            postOnSecondary(Runnable {
                authManager.logInWithEmailAndPassword(
                    email_login.text.toString(),
                    password_login.text.toString()
                )
            })
        }
    }

    override fun updateUI() {
        postOnUI(Runnable {
            when (authManager.loginScreenState) {
                AuthenticationManager.LoginScreenState.INIT -> {
                    loading.visibility = View.INVISIBLE
                }
                AuthenticationManager.LoginScreenState.LOADING -> {
                    loading.visibility = View.VISIBLE
                }
                AuthenticationManager.LoginScreenState.AUTH -> {
                    authManager.setBackToInit()
                    showToast("User logged in")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
                AuthenticationManager.LoginScreenState.FAILED_AUTH -> {
                    authManager.setBackToInit()
                    showToast("Email or password incorrect.")
                }
                AuthenticationManager.LoginScreenState.FAILED_CONNECT -> {
                    authManager.setBackToInit()
                    showToast("Failed to connect.")
                }
            }

        })
    }

    private fun checkValidation(email: String, password: String): Boolean {
        var result = true
        authManager.validateLoginFields(email, password)
        when (authManager.emailState.isValid) {
            false -> {
                email_login.error = authManager.emailState.message
                email_login.requestFocus()
                result = false
            }
        }
        when (authManager.passwordState.isValid) {
            false -> {
                password_login.error = authManager.passwordState.message
                password_login.requestFocus()
                result = false
            }
        }
        return result
    }

    fun signUpButtonClicked(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}