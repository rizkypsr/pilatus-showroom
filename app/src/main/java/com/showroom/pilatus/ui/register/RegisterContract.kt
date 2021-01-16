package com.showroom.pilatus.ui.register

import android.net.Uri
import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.request.RegisterRequest
import com.showroom.pilatus.model.response.login.LoginResponse

interface RegisterContract {

    interface View : BaseView {
        fun onRegisterSuccess(loginResponse: LoginResponse, view: android.view.View)
        fun onRegisterFailed(message: String)
        fun onRegisterPhotoSuccess(photo: List<String>, viewParms: android.view.View)
        fun onRegisterPhotoFailed(message: String)
    }

    interface Presenter : RegisterContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, viewParms: android.view.View)
        fun submitPhotoRegister(filePath: Uri, viewParms: android.view.View)
    }

}