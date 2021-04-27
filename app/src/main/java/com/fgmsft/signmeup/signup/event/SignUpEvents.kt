package com.fgmsft.signmeup.signup.event

import com.fgmsft.signmeup.signup.model.SignUpForm

/**
 * An events class which represents different events for the sign up flow. Add more data classes
 * to add more events.
 */
sealed class SignUpEvents : FgEvent() {
    data class Success(val signUpForm: SignUpForm) : FgEvent()
    data class Error(val messageId: Int) : FgEvent()
}
