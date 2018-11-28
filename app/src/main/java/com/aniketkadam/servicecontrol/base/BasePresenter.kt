package com.aniketkadam.servicecontrol.base

import androidx.annotation.CallSuper
import androidx.annotation.RestrictTo
import com.aniketkadam.servicecontrol.base.mvp.IBasePresenter
import com.aniketkadam.servicecontrol.base.mvp.IBaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter : IBasePresenter {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @CallSuper
    override fun onStart(view: IBaseView) {
        // When the view state has completed emitting (onStop) then everything should be unsubscribed.
        autoUnsubscribe(view.viewState().doOnComplete { dispose() }.subscribe())
    }

    @CallSuper
    protected fun dispose() {
        compositeDisposable.dispose()
    }

    @CallSuper
    protected fun autoUnsubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    @RestrictTo(RestrictTo.Scope.TESTS)
    fun getDisposable() = compositeDisposable
}