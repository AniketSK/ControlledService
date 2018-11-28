package com.aniketkadam.servicecontrol.demoactivity

import android.os.Bundle
import com.aniketkadam.servicecontrol.R
import com.aniketkadam.servicecontrol.base.BaseActivity
import com.aniketkadam.servicecontrol.base.extensions.exhaustive
import com.aniketkadam.servicecontrol.base.models.Running
import com.aniketkadam.servicecontrol.base.models.ServiceStates
import com.aniketkadam.servicecontrol.base.mvp.IBasePresenter
import com.aniketkadam.servicecontrol.demoservice.DemoForegroundService
import com.jakewharton.rxbinding3.widget.checkedChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityPresenter>(), MainActivityContract.View {
    override fun switchToggle(): Observable<Boolean> = serviceActivationSwitch.checkedChanges()

    override fun serviceRun(serviceStates: ServiceStates) = when (serviceStates) {
        ServiceStates.NotRunning -> DemoForegroundService.stopService(this)
        is Running -> {
            DemoForegroundService.startService(this)
            DemoForegroundService.manageNotification(this, serviceStates)
        }
    }.exhaustive

    override fun getPresenter(): IBasePresenter {
        return ActivityPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
