package com.showroom.pilatus.ui.orders

import android.net.Uri
import com.showroom.pilatus.base.BasePresenter
import com.showroom.pilatus.base.BaseView
import com.showroom.pilatus.model.response.transaction.TransactionData

interface UploadContract {

    interface View : BaseView {
        fun onUploadPaymentSuccess(data: List<String>)
        fun onUploadPaymentFailed(message: String)
    }

    interface Presenter : UploadContract, BasePresenter {
        fun uploadPayment(id: String, filePath: Uri)
    }

}