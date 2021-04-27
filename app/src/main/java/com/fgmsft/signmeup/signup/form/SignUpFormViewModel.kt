package com.fgmsft.signmeup.signup.form

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fgmsft.signmeup.signup.event.FgEvent
import com.fgmsft.signmeup.signup.form.model.SignUpFormState
import com.fgmsft.signmeup.signup.model.SignUpForm

/**
 * View Model to handle the validation of the sign up screen.
 *
 * This class is responsible for validating the form fields, calling the sign up service.
 */
class SignUpFormViewModel : ViewModel() {

    // Create a Mutable Live data so that view can listen to the form fields states.
    private val _signupForm = MutableLiveData<SignUpFormState>()
    val signupState: LiveData<SignUpFormState> = _signupForm

    // Mutable live data for the view to listen to the different events such as api success/failure
    private val _events = MutableLiveData<FgEvent>()
    val signUpEvents: LiveData<FgEvent> get() = _events

    /**
     * Function to validate the given email address.
     *
     * @param email
     */
    fun validateEmail(email: String?) {
        TODO("Implement logic to validate email")
    }

    /**
     * Validate the password as per the defined logic
     */
    fun validatePassword(password: String?) {
        TODO("Implement logic to validate password")
    }

    /**
     * Call the sign up service and take appropriate actions.
     * As per the scope of this application, this function will probably always return
     * success in terms of [SignUpEvents]
     */
    fun signUpUser(signUpForm: SignUpForm?) {
        TODO("Implement logic to sign up user")
    }
}