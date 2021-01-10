package com.showroom.pilatus.ui.register

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.showroom.pilatus.MainActivity
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityRegisterBinding
import com.showroom.pilatus.model.request.RegisterRequest
import com.showroom.pilatus.model.response.login.LoginResponse

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter
    private var progressDialog: Dialog? = null
    private lateinit var data: RegisterRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        presenter = RegisterPresenter(this)

        initViews()

        binding.btnRegister.setOnClickListener {

            val edtName = binding.textFieldName.editText?.text.toString()
            val edtEmail = binding.textFieldEmail.editText?.text.toString()
            val edtPassword = binding.textFieldPassword.editText?.text.toString()
            val edtPasswordConfirm = binding.textFieldPassword.editText?.text.toString()
            val edtAddress = binding.textFieldAddress.editText?.text.toString()
            val edtCity = binding.textFieldCity.editText?.text.toString()
            val edtHouseNumber = binding.textFieldHouseNo.editText?.text.toString()
            val edtPhoneNumber = binding.textFieldPhone.editText?.text.toString()

            when {
                edtName.isEmpty() -> {
                    binding.textFieldEmail.error = "Name field cannot be empty !"
                    return@setOnClickListener
                }
                edtEmail.isEmpty() -> {
                    binding.textFieldPassword.error = "Email field cannot be empty !"
                    return@setOnClickListener
                }
                edtPassword.isEmpty() -> {
                    binding.textFieldPassword.error = "Password field cannot be empty !"
                    return@setOnClickListener
                }
                edtAddress.isEmpty() -> {
                    binding.textFieldPassword.error = "Address field cannot be empty !"
                    return@setOnClickListener
                }
                edtCity.isEmpty() -> {
                    binding.textFieldPassword.error = "City field cannot be empty !"
                    return@setOnClickListener
                }
                edtHouseNumber.isEmpty() -> {
                    binding.textFieldPassword.error = "House no field cannot be empty !"
                    return@setOnClickListener
                }
                edtPhoneNumber.isEmpty() -> {
                    binding.textFieldPassword.error = "Phone number field cannot be empty !"
                    return@setOnClickListener
                }
            }

            data = RegisterRequest(
                edtName,
                edtEmail,
                edtPassword,
                edtPasswordConfirm,
                edtAddress,
                edtCity,
                edtHouseNumber,
                edtPhoneNumber
            )

            presenter.submitRegister(data, it)

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

    override fun onRegisterSuccess(loginResponse: LoginResponse, view: View) {

        PilatusShowroom.getApp().setToken(loginResponse.accessToken)

        val gson = Gson()
        val json = gson.toJson(loginResponse.user)

        PilatusShowroom.getApp().setUser(json)

        val moveIntent = Intent(this, MainActivity::class.java)
        startActivity(moveIntent)
    }

    override fun onRegisterFailed(view: String) {
        Log.d("JUNET", "onRegisterFailed: $view")
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()

    }
}