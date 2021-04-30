package com.fgmsft.signmeup.signup.form.ui

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.fgmsft.signmeup.signup.form.avatar.AvatarCameraProvider
import com.fgmsft.signmeup.signup.form.avatar.AvatarManager
import java.io.File

/**
 * View Model class to offer avatar related APIs. This will also help in
 * managing device orientation changes since it can hold the avatar attributes such
 * as absolute path.
 *
 * Currently we are only using camera provider, but if the requirements are extended
 * this view model can incorporate a service which provide an instance of [AvatarManager]
 * based on the type of provider required.
 */
class SignUpAvatarViewModel: ViewModel() {

    private var avatarManager: AvatarManager = AvatarCameraProvider()

    /**
     * Get the intent for capturing the image
     */
    fun getAvatarIntent() : Intent {
        return avatarManager.getAvatarIntent()
    }

    /**
     * Get the required permission for capturing the image
     */
    fun getRequiredAvatarPermission(): String? {
        return avatarManager.getRequiredAvatarPermission()
    }

    /**
     * Create the avatar file where image would be stored.
     */
    fun createAvatarFile(storageDir: File?): File? {
        return avatarManager.createAvatarFile(storageDir)
    }

    /**
     * @return Path to the avatar picture
     */
    fun getAvatarPath(): String? {
        return avatarManager.getAvatarPath()
    }
}