package com.fgmsft.signmeup.signup.event

/**
 * Base Event class.
 */
open class FgEvent {

    private var hasBeenHandled = false

    // handle event
    fun getIfNotHandled(): FgEvent? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            this
        }
    }

    // get event even if it was handled
    fun peekEvent(): FgEvent = this
}