package com.aniketkadam.servicecontrol

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ActivityPresenter : MainActivityContract.Presenter {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStart(view: MainActivityContract.View) {
        autoUnsubscribe(view.switchToggle() // Since we're in the activity, notif's are hidden. If service is off, it's notrunning.
            .map { switchOn ->
                if (switchOn) Running.NotificationHidden else ServiceStates.NotRunning

            }
            .subscribe { view.serviceRun(it) }
        )
    }

    override fun onStop(isFinishing: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun autoUnsubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}