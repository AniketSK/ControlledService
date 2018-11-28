package com.aniketkadam.servicecontrol

/**
 * The various states the activity can be in.
 *
 */
sealed class ViewState {
    /**
     * onStart -> Starting
     */
    object Starting : ViewState()

    /**
     * onStop -> Stopping
     * onStop can be called by a rotation, in which case its finishing is false, otherwise finishing is true since it's
     * not going to come back anytime soon.
     */
    data class Stopping(val finishing: Boolean) : ViewState()
}
