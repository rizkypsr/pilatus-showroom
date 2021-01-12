package com.showroom.pilatus.ui.category

import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoryPresenter(private val view: CategoryContract.View) : CategoryContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun getCategory() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.status.equals("success", true)) {
                        it.data?.let { it1 -> view.onCategorySuccess(it1) }
                    } else {
                        it.meta.message.let { it1 -> view.onCategoryFailed(it1) }
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