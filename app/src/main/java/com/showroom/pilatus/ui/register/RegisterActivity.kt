package com.showroom.pilatus.ui.register

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.showroom.pilatus.MainActivity
import com.showroom.pilatus.PilatusShowroom
import com.showroom.pilatus.R
import com.showroom.pilatus.databinding.ActivityRegisterBinding
import com.showroom.pilatus.model.request.RegisterRequest
import com.showroom.pilatus.model.response.login.LoginResponse
import com.showroom.pilatus.model.response.login.User
import com.showroom.pilatus.utils.Constants

class RegisterActivity : AppCompatActivity(), RegisterContract.View {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var presenter: RegisterPresenter
    private var progressDialog: Dialog? = null
    private lateinit var data: RegisterRequest

    private lateinit var user: User

    var filePath: Uri? = null

    fun initDummy() {
        binding.apply {
            textFieldName.editText?.setText("Abang")
            textFieldEmail.editText?.setText("abang@a.com")
            textFieldPassword.editText?.setText("password")
            textFieldAddress.editText?.setText("Abang jalan")
            textFieldCity.editText?.setText("Abang kota")
            textFieldHouseNo.editText?.setText("11")
            textFieldPhone.editText?.setText("098765678")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        presenter = RegisterPresenter(this)

        initViews()
        initDummy()

        binding.ivProfile.setOnClickListener {
            ImagePicker.with(this).cameraOnly().start()
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            filePath = data?.data

            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfile)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
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

        user = loginResponse.user

        PilatusShowroom.getApp().setUser(Gson().toJson(user))

        if (filePath != null) {
            presenter.submitPhotoRegister(filePath!!, view)
        }
    }

    override fun onRegisterFailed(message: String) {
        Toast.makeText(this, "Register " + message, Toast.LENGTH_SHORT).show()
    }

    override fun onRegisterPhotoSuccess(photo: List<String>, viewParms: View) {

        user.picturePath = Constants.BASE_URL_PHOTO + photo[0]

        PilatusShowroom.getApp().setUser(Gson().toJson(user))

        Log.d("check", "onRegisterPhotoSuccess: $user")

        val moveIntent = Intent(this, MainActivity::class.java)
        startActivity(moveIntent)
    }

    override fun onRegisterPhotoFailed(message: String) {
        Log.d("check", "onRegisterPhotoFailed: $message")
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun dismissLoading() {
        progressDialog?.dismiss()

    }
}