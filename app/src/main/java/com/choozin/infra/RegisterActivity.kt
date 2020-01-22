package com.choozin.infra

import android.os.Bundle
import android.view.View
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.managers.RegisterManager
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    private val registerManager: RegisterManager = RegisterManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        this.updateUI()
    }

    fun backButtonClicked(view : View) {
        super.onBackPressed()
    }

    fun registerButtonClicked(view: View) {
        if(checkValidation()) {
            postOnSecondary(Runnable {
                registerManager.signUp(
                    emailField.text.toString(),
                    passwordField.text.toString(),
                    usernameField.text.toString(),
                    this
                )
            })
        }
    }

    override fun updateUI() {
        postOnUI(Runnable {
            when (registerManager.registerState) {
                RegisterManager.RegisterState.INIT -> {
                    registerProgressBar.visibility = View.INVISIBLE
                }
                RegisterManager.RegisterState.LOADING -> {
                    registerProgressBar.visibility = View.VISIBLE
                }
                RegisterManager.RegisterState.VALID -> {
                    registerManager.setBackToInit()
                    showToast("User created successfully!")
                    super.onBackPressed()
                }
                RegisterManager.RegisterState.UNVALID -> {
                    registerManager.setBackToInit()
                    showToast("Email or Username are taken.")
                }
                RegisterManager.RegisterState.FAILED_CONNECTION -> {
                    registerManager.setBackToInit()
                    showToast("Failed to connect.")
                }
            }

        })
    }

    private fun checkValidation() : Boolean {
        var result = true
        registerManager.validateFields(
            emailField.text.toString(),
            passwordField.text.toString(),
            usernameField.text.toString()
        )
        when(registerManager.emailState.isValid) {
            false -> {
                emailField.error = registerManager.emailState.message
                emailField.requestFocus()
                result = false
            }
        }
        when (registerManager.usernameState.isValid) {
            false -> {
                usernameField.error = registerManager.usernameState.message
                usernameField.requestFocus()
                result = false
            }
        }
        when(registerManager.passwordState.isValid) {
            false -> {
                passwordField.error = registerManager.passwordState.message
                passwordField.requestFocus()
                result = false
            }
        }
        return result
    }

}
