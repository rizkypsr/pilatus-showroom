package com.showroom.pilatus.ui.search

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data

interface SearchContract {

    interface View : BaseView {
        fun onSearchByNameSuccess(product: List<Data>)
        fun onSearchByNameFailed(message: String)
    }

    interface Presenter : SearchContract, BasePresenter {
        fun getSearchByName(name: String)
    }

}