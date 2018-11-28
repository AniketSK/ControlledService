package com.aniketkadam.servicecontrol

import com.aniketkadam.servicecontrol.base.models.Running
import com.aniketkadam.servicecontrol.base.models.ServiceStates
import com.aniketkadam.servicecontrol.base.models.ViewState
import com.aniketkadam.servicecontrol.demoactivity.ActivityPresenter
import com.aniketkadam.servicecontrol.demoactivity.MainActivityContract
import com.aniketkadam.servicecontrol.demoactivity.model.DataSource
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class ActivityPresenterTest {

    // Mocking creates an object out of an interface, but sets everything in it to null.
    @Mock
    lateinit var view: MainActivityContract.View
    @Mock
    lateinit var dataSource: DataSource
    lateinit var presenter: ActivityPresenter
    lateinit var switchToggle: PublishSubject<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        `when`(view.viewState()).thenReturn(PublishSubject.create())
        switchToggle = PublishSubject.create()
        `when`(view.switchToggle()).thenReturn(switchToggle)
        presenter = ActivityPresenter(dataSource)
    }

    @Test
    fun `when the activity starts if the toggle is on, service starts and notification is hidden`() {

        /* Since we have a 'mock' of a view, we need to define exact what each of its functions and fields return
        * In this case, since the interface defines an observable must be returned for switch toggle,
          */
        `when`(view.switchToggle()).thenReturn(Observable.just(true))

        presenter.onStart(view)

        verify(view).serviceRun(Running.NotificationHidden) // Service is running but notification is hidden
    }

    @Test
    fun `when the toggle is switched off, the service stops`() {

        // Now we need to be able to simulate toggling of the switch,
        // to this end, we create a subscription that we control and can then pass whatever values we want later.
        val switchToggle: PublishSubject<Boolean> = PublishSubject.create()
        `when`(view.switchToggle()).thenReturn(switchToggle)

        presenter.onStart(view)

        // Toggle is set to off
        switchToggle.onNext(false)

        verify(view).serviceRun(ServiceStates.NotRunning) // Service is not running since it was toggled off
    }

    @Test
    fun `when the activity stops, if the service is still running, the notification is shown`() {
        `when`(view.switchToggle()).thenReturn(Observable.just(true)) // Service should be running

        val viewStateSubject: PublishSubject<ViewState> = PublishSubject.create()
        `when`(view.viewState()).thenReturn(viewStateSubject)

        presenter.onStart(view)
        verify(view).serviceRun(Running.NotificationHidden)

        viewStateSubject.onNext(ViewState.Stopping(true)) // The view is stopping and it was doing so to finish not rotate.

        verify(view).serviceRun(Running.NotificationVisible) // Keep the service running, with the notification
    }

    @Test
    fun `when the activity stops, all subscriptions are released`() {

        val switchToggle = Observable.just(true)
        `when`(view.switchToggle()).thenReturn(switchToggle)

        val viewStateSubject: PublishSubject<ViewState> = PublishSubject.create()
        `when`(view.viewState()).thenReturn(viewStateSubject)


        presenter.onStart(view)

        assertThat("is empty before disposing of it", presenter.getDisposable().size() != 0)

        viewStateSubject.onNext(ViewState.Stopping(true))
        viewStateSubject.onComplete()

        assertThat("was not disposed", presenter.getDisposable().isDisposed)
        assertThat("is empty", presenter.getDisposable().size() == 0)

    }

    @Test
    fun `switch state is set when the view is begun`() {
        `when`(view.switchToggle()).thenReturn(Observable.just(true))
        `when`(dataSource.isServiceActive()).thenReturn(true)

        presenter.onStart(view)

        verify(view).setSwitchChecked(true)
    }

    @Test
    fun `when the switch is toggled, the state is saved in the data store`() {
        presenter.onStart(view)
        switchToggle.onNext(false)

        verify(dataSource, times(1)).setServiceActive(false)
    }
}