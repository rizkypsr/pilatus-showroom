package com.showroom.pilatus.ui.product

import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProductByCategoryPresenter(private val view: ProductByCategoryContract.View) :
    ProductByCategoryContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getProductByCategory(categoryId: Int) {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.getProductsByCategory(categoryId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onProductByCategorySuccess(it1) }
                    } else {
                        it.meta.message.let { it1 -> view.onProductByCategoryFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onProductByCategoryFailed(it.message.toString())
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