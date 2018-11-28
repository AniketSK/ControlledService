package com.aniketkadam.servicecontrol

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.withLatestFrom

class ActivityPresenter : MainActivityContract.Presenter {
    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onStart(view: MainActivityContract.View) {

        // When the view state has completed emitting (onStop) then everything should be unsubscribed.
        autoUnsubscribe(view.viewState().doOnComplete { compositeDisposable.dispose() }.subscribe())

        // Since we're in the activity, notifs are hidden. If service is off, it's notrunning.
        autoUnsubscribe(view.switchToggle()
            .map { switchOn ->
                if (switchOn) Running.NotificationHidden else ServiceStates.NotRunning

            }
            .subscribe { view.serviceRun(it) }
        )

        // If it's stopping, and the service is supposed to be on, show the notification.
        autoUnsubscribe(
            view.viewState()
                .withLatestFrom(view.switchToggle())
                .filter { (vs, serviceOn) -> vs == ViewState.Stopping(true) && serviceOn }
                .map { Running.NotificationVisible }
                .subscribe { view.serviceRun(it) }
        )

    }

    fun autoUnsubscribe(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }
}