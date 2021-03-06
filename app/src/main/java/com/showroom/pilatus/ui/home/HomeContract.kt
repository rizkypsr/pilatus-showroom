package com.showroom.pilatus.ui.home

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data

interface HomeContract {

    interface View : BaseView {
        fun onProductSuccess(it1: List<Data>)
        fun onProductFailed(message: String)

        fun onCategorySuccess(categoryResponse: List<CategoryResponse>)
        fun onCategoryFailed(message: String)
    }

    interface Presenter : HomeContract, BasePresenter {
        fun getProduct()
        fun getCategory()
    }

}