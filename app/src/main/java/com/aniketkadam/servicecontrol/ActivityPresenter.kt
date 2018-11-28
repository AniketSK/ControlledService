package com.aniketkadam.servicecontrol

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ActivityPresenter : MainActivityContract.Presenter {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStart(view: MainActivityContract.View) {

        // Since we're in the activity, notifs are hidden. If service is off, it's notrunning.
        autoUnsubscribe(view.switchToggle()
            .map { switchOn ->
                if (switchOn) Running.NotificationHidden else ServiceStates.NotRunning

            }
            .subscribe { view.serviceRun(it) }
        )
    }

    fun autoUnsubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}