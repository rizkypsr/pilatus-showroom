package com.showroom.pilatus.ui.payment

import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.checkout.CheckoutResponse

interface PaymentContract {

    interface View : BaseView {
        fun onCheckoutSuccess(checkoutResponse: CheckoutResponse, view: android.view.View)
        fun onCheckoutFailed(message: String)
    }

    interface Presenter : PaymentContract, BasePresenter {
        fun getCheckout(
            productId: String,
            userId: String,
            quantity: String,
            total: String,
            viewParms: android.view.View
        )
    }

}