package com.showroom.pilatus.ui.orders

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.transaction.TransactionResponseItem

interface OrderContract {

    interface View : BaseView {
        fun onTransactionSuccess(data: List<TransactionResponseItem>)
        fun onTransactionFailed(message: String)
    }

    interface Presenter : OrderContract, BasePresenter {
        fun getTransaction()
    }

}