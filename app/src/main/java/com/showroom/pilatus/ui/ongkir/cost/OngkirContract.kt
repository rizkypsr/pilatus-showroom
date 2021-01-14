package com.showroom.pilatus.ui.ongkir.cost

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.ongkir.city.Result
import com.showroom.pilatus.model.response.ongkir.cost.Cost

interface OngkirContract {

    interface View : BaseView {
        fun onCourierSuccess(cost: List<Cost>)
        fun onCourierFailed(message: String)
    }

    interface Presenter : OngkirContract, BasePresenter {
        fun getCourier(origin: String, destination: String, weight: Int, courier: String)
    }

}