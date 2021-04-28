package com.fgmsft.signmeup.signup.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Data class to hold the user sign up information.
 *
 * @param firstName - Optional
 * @param email - Required
 * @param password - Required
 * @param website - Optional
 * @param avatarPath - Optional
 */
@Parcelize
data class SignUpForm(
    /**
     * First name of the user
     */
    val firstName: String? = null,

    /**
     * Email id of the user
     */
    val email: String,

    /**
     * Password for the account
     */
    val password: String,

    /**
     * User website information.
     */
    val website: String? = null,

    /**
     * Path to the avatar picture
     */
    val avatarPath: String? = null
): Parcelable
