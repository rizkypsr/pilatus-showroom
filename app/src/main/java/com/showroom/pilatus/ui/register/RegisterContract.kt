package com.showroom.pilatus.ui.register

import android.net.Uri
import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.request.RegisterRequest
import com.showroom.pilatus.model.response.login.LoginResponse

interface RegisterContract {

    interface View : BaseView {
        fun onRegisterSuccess(loginResponse: LoginResponse, view: android.view.View)
//        fun onRegisterPhotoSuccess(view: android.view.View)
//        fun onRegisterPhotoFailed(message: String)
        fun onRegisterFailed(view: String)
    }

    interface Presenter : RegisterContract, BasePresenter {
        fun submitRegister(registerRequest: RegisterRequest, view: android.view.View)
//        fun submitPhoto(filePath: Uri, view: android.view.View)
    }

}