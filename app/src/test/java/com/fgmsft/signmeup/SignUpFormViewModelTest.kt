package com.fgmsft.signmeup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fgmsft.signmeup.signup.event.FgEvent
import com.fgmsft.signmeup.signup.event.SignUpEvents
import com.fgmsft.signmeup.signup.form.SignUpFormViewModel
import com.fgmsft.signmeup.signup.form.model.SignUpFormState
import com.fgmsft.signmeup.signup.model.SignUpForm
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SignUpFormViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var signUpformStateObserver: Observer<SignUpFormState>

    @RelaxedMockK
    private lateinit var signUpEventObserver: Observer<FgEvent>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `valid email test`() {
        val validEmail = "myvalidemail@myvalidemail.com"

        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signupState.observeForever(signUpformStateObserver)
        signUpViewModel.validateEmail(validEmail)

        verify {
            signUpformStateObserver.onChanged(SignUpFormState(emailError = null, isDataValid = false))
        }

        signUpViewModel.signupState.removeObserver(signUpformStateObserver)
    }

    @Test
    fun `Invalid email test`() {
        val validEmail = "myInvalidEmail"

        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signupState.observeForever(signUpformStateObserver)
        signUpViewModel.validateEmail(validEmail)

        verify {
            signUpformStateObserver.onChanged(SignUpFormState(emailError = R.string.email_error, isDataValid = false))
        }

        signUpViewModel.signupState.removeObserver(signUpformStateObserver)
    }

    @Test
    fun `valid password test`() {
        val validPassword = "asdsad!%#ijsdjs"

        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signupState.observeForever(signUpformStateObserver)
        signUpViewModel.validatePassword(validPassword)

        verify {
            signUpformStateObserver.onChanged(SignUpFormState(passwordError = null, isDataValid = false))
        }

        signUpViewModel.signupState.removeObserver(signUpformStateObserver)
    }

    @Test
    fun `Invalid password test`() {
        val validPassword = "asd"

        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signupState.observeForever(signUpformStateObserver)
        signUpViewModel.validatePassword(validPassword)

        verify {
            signUpformStateObserver.onChanged(SignUpFormState(passwordError = R.string.password_error, isDataValid = false))
        }

        signUpViewModel.signupState.removeObserver(signUpformStateObserver)
    }

    @Test
    fun `valid form test`() {
        val validEmail = "myvalidemail@myvalidemail.com"
        val validPassword = "asdsad!%#ijsdjs"

        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signupState.observeForever(signUpformStateObserver)
        signUpViewModel.validateEmail(validEmail)
        signUpViewModel.validatePassword(validPassword)

        verify {
            signUpformStateObserver.onChanged(SignUpFormState(passwordError = null, emailError = null, isDataValid = true))
        }

        signUpViewModel.signupState.removeObserver(signUpformStateObserver)
    }

    @Test
    fun `sign up user success test`() {
        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signUpEvents.observeForever(signUpEventObserver)
        val signUpForm = SignUpForm(email = "", password = "")
        signUpViewModel.signUpUser(signUpForm)

        verify {
            signUpEventObserver.onChanged(SignUpEvents.Success(signUpForm))
        }

        signUpViewModel.signUpEvents.removeObserver(signUpEventObserver)
    }

    @Test
    fun `sign up user error test`() {
        val signUpViewModel = SignUpFormViewModel()

        signUpViewModel.signUpEvents.observeForever(signUpEventObserver)

        signUpViewModel.signUpUser(null)

        verify {
            signUpEventObserver.onChanged(SignUpEvents.Error(R.string.signup_fields_error))
        }
    }
}