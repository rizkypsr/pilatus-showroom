package com.showroom.pilatus.ui.login

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.login.LoginResponse

interface LoginContract {

    interface View : BaseView {
        fun onLoginSuccess(loginResponse: LoginResponse)
        fun onLoginFailed(message: String)
    }

    interface Presenter : LoginContract, BasePresenter {
        fun submitLogin(email: String, password: String)
    }

}