package com.fgmsft.signmeup.signup.form.model

/**
 * This class will hold the validation state of the sign up form.
 *
 * @param emailError resource id for the message to be shown. Could be null if there is no error
 * @param passwordError resource id for the message to be shown. Could be null if there is not error
 * @param isDataValid returns true if the required fields are valid, false otherwise.
 *
 * This could also be extended by incorporating individual field error boolean values.
 */
data class SignUpFormState(
    var emailError: Int? = null,
    var passwordError: Int? = null,
    var isDataValid: Boolean = false
)
