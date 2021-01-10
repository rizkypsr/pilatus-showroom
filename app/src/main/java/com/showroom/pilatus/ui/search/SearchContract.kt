package com.showroom.pilatus.ui.search

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data

interface SearchContract {

    interface View : BaseView {
        fun onProductByCategorySuccess(it1: List<Data>)
        fun onProductByCategoryFailed(message: String)
    }

    interface Presenter : SearchContract, BasePresenter {
        fun getProductByCategory(categoryId: Int)
    }

}