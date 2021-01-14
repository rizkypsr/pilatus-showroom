package com.showroom.pilatus.ui.ongkir.cost

import android.util.Log
import com.showroom.pilatus.network.HttpClientOngkir
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class OngkirPresenter(private val view: OngkirContract.View) : OngkirContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCourier(origin: String, destination: String, weight: Int, courier: String) {
        view.showLoading()
        val disposable = HttpClientOngkir.getInstance().getApi()!!.postCost(
            origin, destination, weight, courier
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.rajaongkir.status.code == 200) {
                        it.rajaongkir.results[0].costs.let { it1 -> view.onCourierSuccess(it1) }
                    } else {
                        it.rajaongkir.status.description.let { it1 -> view.onCourierFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCourierFailed(it.message.toString())
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