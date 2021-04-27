package com.fgmsft.signmeup.signup.form.avatar

import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation class for providing avatar capture via device camera.
 */
class AvatarCameraProvider: AvatarManager {

    /**
     * Absolute path for the captured avatar image
     */
    private var avatarPath: String? = null

    companion object {
        private const val FILE_PREFIX = "Avatar_"
        private const val FILE_SUFFIX = ".jpg"
    }

    /**
     * Get the required permission for capturing the image
     */
    override fun getRequiredAvatarPermission(): String? {
        return Manifest.permission.CAMERA
    }

    /**
     * Get the intent for capturing the image
     */
    override fun getAvatarIntent(): Intent {
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    }

    /**
     * Create the avatar file where image would be stored.
     */
    override fun createAvatarFile(storageDir: File?): File? {
        if (storageDir == null) {
            return null
        }

        // Unique identifier for the image files.
        val timestamp: String = SimpleDateFormat("yyyyMMDD_HHmmss").format(Date())

        return File.createTempFile("$FILE_PREFIX${timestamp}", FILE_SUFFIX, storageDir).apply {
            avatarPath = absolutePath
        }
    }

    /**
     * @return Path to the avatar picture
     */
    override fun getAvatarPath(): String? {
        return avatarPath
    }
}