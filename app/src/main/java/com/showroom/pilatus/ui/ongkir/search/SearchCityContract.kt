package com.showroom.pilatus.ui.ongkir.search

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.ongkir.city.Result

interface SearchCityContract {

    interface View : BaseView {
        fun onCitySuccess(city: List<Result>)
        fun onCityFailed(message: String)
    }

    interface Presenter : SearchCityContract, BasePresenter {
        fun getCity()
    }

}