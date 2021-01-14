package com.showroom.pilatus.ui.changeProfile

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.login.LoginResponse
import com.showroom.pilatus.model.response.login.User

interface ProfileContract {

    interface View : BaseView {
        fun onChangeProfileSuccess(user: User)
        fun onChangeProfileFailed(message: String)
    }

    interface Presenter : ProfileContract, BasePresenter {
        fun changeProfile(
            name: String,
            email: String,
            address: String,
            city: String,
            houseNumber: String,
            phoneNumber: String
        )
    }

}