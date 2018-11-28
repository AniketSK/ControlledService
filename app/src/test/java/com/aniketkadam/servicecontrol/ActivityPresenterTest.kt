package com.aniketkadam.servicecontrol

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class ActivityPresenterTest {

    // Mocking creates an object out of an interface, but sets everything in it to null.
    @Mock
    lateinit var view: MainActivityContract.View
    lateinit var presenter: ActivityPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ActivityPresenter()
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
        val subject: PublishSubject<Boolean> = PublishSubject.create()
        `when`(view.switchToggle()).thenReturn(subject)

        presenter.onStart(view)

        // Toggle is set to off
        subject.onNext(false)

        verify(view).serviceRun(ServiceStates.NotRunning) // Service is not running since it was toggled off
    }

    @Test
    fun `when the activity stops, if the service is still running, the notification is shown`() {
        `when`(view.switchToggle()).thenReturn(Observable.just(true)) // Service should be running

        presenter.onStart(view)
        presenter.onStop(true) // The view is stopping and it was doing so to finish not rotate.

        verify(view).serviceRun(Running.NotificationVisible) // Keep the service running, with the notification
    }
}