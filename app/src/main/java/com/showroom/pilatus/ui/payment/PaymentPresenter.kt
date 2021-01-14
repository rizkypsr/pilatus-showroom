package com.showroom.pilatus.ui.payment

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
        productId: Int,
        userId: Int,
        quantity: Int,
        total: Long,
        courierType: String,
        courierPrice: Long
    ) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.checkout(
            productId, userId, quantity, total, "PENDING", courierType, courierPrice
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.code == 200) {
                        it.data.let { it1 -> view.onCheckoutSuccess(it1!!) }
                    } else {
                        it.meta.message.let { it1 -> view.onCheckoutFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCheckoutFailed(it.message.toString())
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