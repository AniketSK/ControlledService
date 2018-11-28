package com.aniketkadam.servicecontrol.demoactivity

import com.aniketkadam.servicecontrol.base.BasePresenter
import com.aniketkadam.servicecontrol.base.models.Running
import com.aniketkadam.servicecontrol.base.models.ServiceStates
import com.aniketkadam.servicecontrol.base.models.ViewState
import io.reactivex.rxkotlin.withLatestFrom

class ActivityPresenter : BasePresenter() {

    fun onStart(view: MainActivityContract.View) {
        super.onStart(view)
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

}