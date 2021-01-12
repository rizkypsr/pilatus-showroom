package com.showroom.pilatus.ui.product

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.home.Data

interface ProductByCategoryContract {

    interface View : BaseView {
        fun onProductByCategorySuccess(it1: List<Data>)
        fun onProductByCategoryFailed(message: String)
    }

    interface Presenter : ProductByCategoryContract, BasePresenter {
        fun getProductByCategory(categoryId: Int)
    }

}