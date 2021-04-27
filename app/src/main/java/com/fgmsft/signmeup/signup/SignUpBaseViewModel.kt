package com.fgmsft.signmeup.signup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.max
import kotlin.math.min

/**
 * Base view model class for Sign up flow.
 */
abstract class SignUpBaseViewModel: ViewModel() {

    private val _avatarBitmap = MutableLiveData<Bitmap?>()
    val avatarBitmap: LiveData<Bitmap?> = _avatarBitmap

    /**
     * Function responsible for creating bitmap using the avatar path [avatarPath]
     * Subscribe to [avatarBitmap] live data to get the updated Bitmap.
     *
     * @param width The target width of the bitmap
     * @param height The target height of the bitmap
     * @param avatarPath Absolute path to the avatar image
     *
     */
    fun createBitmap(width: Int, height: Int, avatarPath: String? = null) {
        if (avatarPath.isNullOrEmpty()) {
            _avatarBitmap.value = null
            return
        }

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            val avatarWidth: Int = outWidth
            val avatarHeight: Int = outHeight

            // Determine how much to scale down the image

            val scaleFactor: Int = if (width == 0 || height == 0) 1 else max(1, min(avatarWidth / width, avatarHeight / height))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        _avatarBitmap.value = BitmapFactory.decodeFile(avatarPath, bmOptions)
    }
}