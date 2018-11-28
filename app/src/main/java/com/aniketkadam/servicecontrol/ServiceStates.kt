package com.aniketkadam.servicecontrol

/**
 * We want to represent all valid states of the service:
 * 1. The service running WITHOUT a notification.
 * 2. The service running WITH a notification.
 * 3. The service stopped.
 */
sealed class ServiceStates {

    object NotRunning : ServiceStates()
}

/**
 * The class is broken out to ensure that Running (without notification status), isn't valid states.
 * Breaking it out of the hierarchy enforces that the sub states are defined.
 *
 * Separately, we're not using a data class that takes a boolean for the aesthetic choice of something like:
 * when (state) {
 *    Running.NotificationVisible -> //stuff
 *    Running.NotificationHidden -> //other stuff
 *    ServiceStates.NotRunning -> //more stuff
 * }
 *
 * over
 * when (state) {
 *    Running.NotificationVisibility -> if(state.visible) { // stuff} else {//other stuff}
 *    ServiceStates.NotRunning -> // more stuff
 * }
}
 */
sealed class Running : ServiceStates() {
    object NotificationVisible : Running()

    object NotificationHidden : Running()
}
