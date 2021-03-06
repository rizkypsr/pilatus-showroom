package com.showroom.pilatus.ui.home

import android.util.Log
import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getProduct() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.status.equals("success", true)) {
                        Log.d("JUNET", "getProduct: $it")
                        it.data?.let { it1 -> view.onProductSuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onProductFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onProductFailed(it.message.toString())
                }
            )
        mCompositeDisposable!!.add(disposable)
    }

    override fun getCategory() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta?.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onCategorySuccess(it1) }
                    } else {
                        it.meta?.message?.let { it1 -> view.onCategoryFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onCategoryFailed(it.message.toString())
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