package com.showroom.pilatus.ui.orders

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.transaction.TransactionData

interface OrderContract {

    interface View : BaseView {
        fun onTransactionSuccess(data: List<TransactionData>)
        fun onTransactionFailed(message: String)
    }

    interface Presenter : OrderContract, BasePresenter {
        fun getTransaction()
    }

}