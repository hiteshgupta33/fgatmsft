package com.fgmsft.signmeup.signup

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fgmsft.signmeup.R
import com.fgmsft.signmeup.signup.confirmation.SignUpConfirmationFragment
import com.fgmsft.signmeup.signup.event.SignUpEvents
import com.fgmsft.signmeup.signup.form.SignUpFormViewModel
import com.fgmsft.signmeup.signup.form.ui.SignUpFormFragment

/**
 * Host activity for the sign up flow.
 */
class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpViewModel: SignUpFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpViewModel = ViewModelProvider(this).get(SignUpFormViewModel::class.java)
        observeSignUpEvents()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SignUpFormFragment.newInstance())
                .commitNow()
        }

    }

    /**
     * Observe to the SignUp Events [SignUpEvents]. The events can be easily extended
     * to other events such as loading to show progress, more error states.
     */
    private fun observeSignUpEvents() {
        signUpViewModel.signUpEvents.observe(this, Observer {
            it.getIfNotHandled()?.let { _event ->
                when (_event) {
                    is SignUpEvents.Success -> {
                        // Ideally data should be retrieved by the fragment via data store and only identifiers should
                        // be sent to the fragment. That would also remove the requirement of making the data class parcelable.
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.container, SignUpConfirmationFragment.newInstance(_event.signUpForm))
                            .commitNow()
                    }

                    is SignUpEvents.Error -> {
                        Toast.makeText(this, getString(R.string.signup_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}