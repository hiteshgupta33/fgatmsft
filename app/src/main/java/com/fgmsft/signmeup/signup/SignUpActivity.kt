package com.fgmsft.signmeup.signup

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.fgmsft.signmeup.R

/**
 * Host activity for the sign up flow.
 */
class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
    }
}