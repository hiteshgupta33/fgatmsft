package com.fgmsft.signmeup.signup.confirmation

import com.fgmsft.signmeup.signup.SignUpBaseViewModel
import com.fgmsft.signmeup.signup.model.SignUpForm

/**
 * View model class containing the logic for the sign up confirmation page.
 *
 * This class should hold any logic to retrieve/manipulate the data.
 */
class SignUpConfirmationViewModel(_signUpForm: SignUpForm?): SignUpBaseViewModel() {

    private var signUpForm: SignUpForm? = null

    init {
        this.signUpForm = _signUpForm
    }

    /**
     * Get the title to be displayed. Currently it checks if the first name is null, returns email
     * else returns first name formatted in the prefix.
     */
    fun getTitle(prefix: String) : String {
        return if (signUpForm?.firstName.isNullOrEmpty()) {
            String.format(prefix, signUpForm?.email)
        } else {
            String.format(prefix, signUpForm?.firstName)
        }
    }

    /**
     * @param value: value based on which the visibility should be determined
     *
     * @return True if the content is not null or empty.
     */
    fun shouldBeVisible(value: String?) : Boolean {
        return !value.isNullOrEmpty()
    }
}