package com.fgmsft.signmeup.signup.form.avatar

import android.content.Intent
import java.io.File

/**
 * This class defines the APIs required to capture/select avatar for the user.
 * Currently its only used by Camera Provider [AvatarCameraProvider] but it could be extended to other
 * providers as well.
 *
 */
interface AvatarManager {

    /**
     * Get the required permission for capturing the image
     */
    fun getRequiredAvatarPermission(): String?

    /**
     * Get the intent for capturing the image
     */
    fun getAvatarIntent(): Intent

    /**
     * Create the avatar file where image would be stored.
     */
    fun createAvatarFile(storageDir: File?): File?

    /**
     * @return Path to the avatar picture
     */
    fun getAvatarPath(): String?
}