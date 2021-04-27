package com.fgmsft.signmeup.signup.form

import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fgmsft.signmeup.R
import com.fgmsft.signmeup.signup.event.FgEvent
import com.fgmsft.signmeup.signup.event.SignUpEvents
import com.fgmsft.signmeup.signup.form.model.SignUpFormState
import com.fgmsft.signmeup.signup.model.SignUpForm

/**
 * View Model to handle the validation of the sign up screen.
 *
 * This class is responsible for validating the form fields, calling the sign up service.
 */
class SignUpFormViewModel : ViewModel() {

    companion object {
        // Minimum required length of the password
        private const val passwordLengthMin = 6
    }

    // Create a Mutable Live data so that view can listen to the form fields states.
    private val _signupForm = MutableLiveData<SignUpFormState>()
    val signupState: LiveData<SignUpFormState> = _signupForm

    // Mutable live data for the view to listen to the different events such as api success/failure
    private val _events = MutableLiveData<FgEvent>()
    val signUpEvents: LiveData<FgEvent> get() = _events

    // Fields to maintain the individual field validation state.
    // This could potentially be incorporated into [SignUpFormState]
    private var validEmail: Boolean = false
    private var validPassword: Boolean = false

    /**
     * Function to validate the given email address. Function currently uses [PatternsCompat.EMAIL_ADDRESS]
     * matcher to validate the email (Can be changed as per requirements in the future).
     *
     * This function also posts the value to LiveData for view to take appropriate action.
     * @param email
     */
    fun validateEmail(email: String?) {
        validEmail = !email.isNullOrEmpty() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()

        _signupForm.value = SignUpFormState(emailError = (if (validEmail) null else R.string.email_error), isDataValid = validateForm())
    }

    /**
     * Validate the password as per the defined logic
     */
    fun validatePassword(password: String?) {
        validPassword = !password.isNullOrEmpty() && password.length > passwordLengthMin

        _signupForm.value = SignUpFormState(passwordError = (if (validPassword) null else R.string.password_error), isDataValid = validateForm())
    }

    /**
     * Call the sign up service and take appropriate actions.
     * This function will return success in terms of [SignUpEvents] if signUpForm is not null
     * else it will post Error event.
     *
     * @param signUpForm [SignUpForm]
     *
     * See [SignUpEvents] to see the possible events posted.
     */
    fun signUpUser(signUpForm: SignUpForm?) {
        if (signUpForm == null) {
            _events.value = SignUpEvents.Error(R.string.signup_error)
        } else {
            _events.value = SignUpEvents.Success(signUpForm)
        }
    }

    /**
     * A simple function to check if all the mandatory fields are validated.
     */
    private fun validateForm(): Boolean {
        return validEmail && validPassword
    }
}