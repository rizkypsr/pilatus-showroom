package com.showroom.pilatus.ui.profile

import com.showroom.pilatus.network.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LogoutPresenter(private val view: LogoutContract.View) : LogoutContract.Presenter {

    private val mCompositeDisposable: CompositeDisposable?

    init {
        this.mCompositeDisposable = CompositeDisposable()
    }

    override fun logout() {
        view.showLoading()
        val disposable = HttpClient.getInstance().getApi()!!.logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view.dismissLoading()
                    if (it.meta.code == 200) {
                        it.data?.let { it1 -> view.onLogoutSuccess(it1) }
                    } else {
                        it.meta.message.let { it1 -> view.onLogoutFailed(it1) }
                    }
                },
                {
                    view.dismissLoading()
                    view.onLogoutFailed(it.message.toString())
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