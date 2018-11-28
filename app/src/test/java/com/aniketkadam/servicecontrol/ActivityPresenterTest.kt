package com.aniketkadam.servicecontrol

import io.reactivex.Observable
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
    fun `when the activity starts if the toggle is on, service starts`() {

        /* Since we have a 'mock' of a view, we need to define exact what each of its functions and fields return
        * In this case, since the interface defines an observable must be returned for switch toggle,
          */
        `when`(view.switchToggle()).thenReturn(Observable.just(true))

        presenter.onStart(view)

        verify(view).serviceRun(true)
    }
}