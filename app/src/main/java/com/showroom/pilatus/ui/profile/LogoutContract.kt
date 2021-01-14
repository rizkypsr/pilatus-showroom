package com.showroom.pilatus.ui.profile

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.login.LoginResponse
import com.showroom.pilatus.model.response.login.User

interface LogoutContract {

    interface View : BaseView {
        fun onLogoutSuccess(data: Boolean)
        fun onLogoutFailed(message: String)
    }

    interface Presenter : LogoutContract, BasePresenter {
        fun logout()
    }

}