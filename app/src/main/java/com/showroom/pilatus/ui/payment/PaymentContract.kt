package com.showroom.pilatus.ui.payment

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.checkout.CheckoutData

interface PaymentContract {

    interface View : BaseView {
        fun onCheckoutSuccess(checkoutResponse: CheckoutData)
        fun onCheckoutFailed(message: String)
    }

    interface Presenter : PaymentContract, BasePresenter {
        fun getCheckout(
            productId: Int,
            userId: Int,
            quantity: Int,
            total: Long,
            courierType: String,
            courierPrice: Long
        )
    }
}