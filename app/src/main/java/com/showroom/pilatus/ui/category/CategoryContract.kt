package com.showroom.pilatus.ui.category

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.home.CategoryResponse
import com.showroom.pilatus.model.response.home.Data

interface CategoryContract {

    interface View : BaseView {
        fun onCategorySuccess(categoryResponse: List<CategoryResponse>)
        fun onCategoryFailed(message: String)
    }

    interface Presenter : CategoryContract, BasePresenter {
        fun getCategory()
    }

}