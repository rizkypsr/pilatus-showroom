package com.showroom.pilatus.ui.ongkir.search

import com.showroom.pilatus.network.HttpClientOngkir
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchCityPresenter(private val view: SearchCityContract.View) : SearchCityContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCity() {
        view.showLoading()
        val disposable = HttpClientOngkir.getInstance().getApi()!!.getCity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.rajaongkir.status.code == 200) {
                        it.rajaongkir.results.let { it1 -> view.onCitySuccess(it1) }
                    } else {
                        it.rajaongkir.status.description.let { it1 -> view.onCityFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCityFailed(it.message.toString())
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