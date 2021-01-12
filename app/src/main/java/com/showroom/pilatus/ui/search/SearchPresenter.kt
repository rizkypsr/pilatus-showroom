package com.showroom.pilatus.ui.search

import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter(private val view: SearchContract.View) : SearchContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getSearchByName(name: String) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.getProductsByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onSearchByNameSuccess(it1) }
                    } else {
                        it.meta.message.let { it1 -> view.onSearchByNameFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onSearchByNameFailed(it.message.toString())
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