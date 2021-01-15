package com.showroom.pilatus.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.showroom.pilatus.MainActivity
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityLoginBinding
import com.showroom.pilatus.model.response.login.LoginResponse
import com.showroom.pilatus.ui.register.RegisterActivity
import com.showroom.pilatus.utils.SessionManager
import kotlin.math.log

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var presenter: LoginPresenter
    private lateinit var sessionManager: SessionManager
    private var progressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sessionManager = SessionManager(this)

        if (!PilatusShowroom.getApp().getToken().isNullOrEmpty()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        presenter = LoginPresenter(this)

        initViews()

        binding.btnLogin.setOnClickListener {
            val edtEmail = binding.textFieldEmail.editText?.text.toString()
            val edtPassword = binding.textFieldPassword.editText?.text.toString()

            when {
                edtEmail.isEmpty() -> {
                    binding.textFieldEmail.error = "Email field cannot be empty !"
                    return@setOnClickListener
                }
                edtPassword.isEmpty() -> {
                    binding.textFieldPassword.error = "Password field cannot be empty !"
                    return@setOnClickListener
                }
            }

            presenter.submitLogin(edtEmail, edtPassword)
        }

        binding.btnTextRegister.setOnClickListener {
            val moveIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun initViews() {
        progressDialog = Dialog(this)
        val dialogLayout = layoutInflater.inflate(R.layout.dialog_loader, null)

        progressDialog?.let {
            it.setContentView(dialogLayout)
            it.setCancelable(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    override fun onLoginSuccess(loginResponse: LoginResponse) {
        PilatusShowroom.getApp().setToken(loginResponse.accessToken)

        val json = Gson().toJson(loginResponse.user)

        PilatusShowroom.getApp().setUser(json)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onLoginFailed(message: String) {
        Toast.makeText(this, "Gagal Login. $message", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressDialog!!.show()
    }

    override fun dismissLoading() {
        progressDialog!!.dismiss()
    }
}