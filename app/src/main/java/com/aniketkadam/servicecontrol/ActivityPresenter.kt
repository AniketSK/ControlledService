package com.aniketkadam.servicecontrol

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ActivityPresenter : MainActivityContract.Presenter {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStart(view: MainActivityContract.View) {
        autoUnsubscribe(view.switchToggle().subscribe({ view.serviceRun(it) }))
    }

    override fun onStop(isFinishing: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun autoUnsubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}