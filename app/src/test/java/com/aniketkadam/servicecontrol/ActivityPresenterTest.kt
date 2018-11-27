package com.aniketkadam.servicecontrol

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ActivityPresenterTest {

    @Mock
    lateinit var view: MainActivityContract.View
    lateinit var presenter: ActivityPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ActivityPresenter()
    }


}