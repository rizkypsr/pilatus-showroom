package com.showroom.pilatus.ui.payment

import android.util.Log
import android.view.View
import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PaymentPresenter(private val view: PaymentContract.View) : PaymentContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCheckout(
        productId: String,
        userId: String,
        quantity: String,
        total: String,
        viewParms: View
    ) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.checkout(
            productId ,userId, quantity, total, "PENDING"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onCheckoutSuccess(it1, viewParms) }
                    } else {
                        it.meta.message.let { it1 -> view.onCheckoutFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCheckoutFailed(it.message.toString())
                    Log.d("TAG", "getCheckout: ${it.cause.toString()}")
                }
            )
        mCompositeDisposable!!.add(disposable)
    }


    override fun subscribe() {

    }

    override fun unSubscribe() {
        mCompositeDisposable!!.clear()
    }
}