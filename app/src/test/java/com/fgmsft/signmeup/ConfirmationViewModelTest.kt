package com.fgmsft.signmeup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fgmsft.signmeup.signup.confirmation.SignUpConfirmationViewModel
import com.fgmsft.signmeup.signup.model.SignUpForm
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ConfirmationViewModelTest {

    @get:Rule
    val instantTaskRule = InstantTaskExecutorRule()

    private val titlePrefix = "%s"

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `get title when first name is null`() {

        val signUpForm = mockk<SignUpForm>()

        val email = "myvalidemail@myvalidemail.com"
        every { signUpForm.firstName } returns null
        every { signUpForm.avatarPath } returns null
        every { signUpForm.email } returns email

        val actual = SignUpConfirmationViewModel(signUpForm).getTitle(titlePrefix)

        Assert.assertEquals(String.format(titlePrefix, email), actual)
    }

    @Test
    fun `get title when first name is NOT null`() {

        val signUpForm = mockk<SignUpForm>()
        val firstName = "Android"
        every { signUpForm.firstName } returns firstName
        every { signUpForm.avatarPath } returns null
        every { signUpForm.email } returns "myvalidemail@myvalidemail.com"

        val actual = SignUpConfirmationViewModel(signUpForm).getTitle(titlePrefix)

        Assert.assertEquals(String.format(titlePrefix, firstName), actual)
    }

    @Test
    fun `check visibility with null value`() {

        val signUpForm = mockk<SignUpForm>()
        every { signUpForm.avatarPath } returns null

        val actual = SignUpConfirmationViewModel(signUpForm).shouldBeVisible(null)

        Assert.assertFalse(actual)
    }

    @Test
    fun `check visibility with NOT null value`() {

        val signUpForm = mockk<SignUpForm>()
        every { signUpForm.avatarPath } returns null

        val actual = SignUpConfirmationViewModel(signUpForm).shouldBeVisible("IamHere")

        Assert.assertTrue(actual)
    }
}