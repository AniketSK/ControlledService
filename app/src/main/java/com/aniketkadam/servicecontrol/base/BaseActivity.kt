package com.aniketkadam.servicecontrol.base

import android.support.v7.app.AppCompatActivity
import androidx.annotation.CallSuper
import com.aniketkadam.servicecontrol.base.models.ViewState
import io.reactivex.subjects.PublishSubject

abstract class BaseActivity<T : IBasePresenter> : AppCompatActivity(), IBaseView {

    private lateinit var presenter: IBasePresenter
    protected val viewStatePublishSubject = PublishSubject.create<ViewState>()

    @CallSuper
    override fun onStart() {
        super.onStart()
        presenter = getPresenter()
        presenter.onStart(this)
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        viewStatePublishSubject.onNext(ViewState.Stopping(isFinishing))
        viewStatePublishSubject.onComplete()
    }

    abstract fun getPresenter(): IBasePresenter
}