package com.showroom.pilatus.ui.home

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.home.Data

interface HomeContract {

    interface View : BaseView {
        fun onProductSuccess(it1: List<Data>)
        fun onProductFailed(message: String)
    }

    interface Presenter : HomeContract, BasePresenter {
        fun getProduct()
    }

}