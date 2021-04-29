package com.fgmsft.signmeup.signup.form.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fgmsft.signmeup.R
import com.fgmsft.signmeup.signup.form.SignUpFormViewModel
import com.fgmsft.signmeup.signup.form.avatar.AvatarCameraProvider
import com.fgmsft.signmeup.signup.form.avatar.AvatarManager
import com.fgmsft.signmeup.signup.permission.PermissionChecker
import java.io.File
import java.io.IOException

/**
 * Fragment class to handle the Avatar permission requests, dispatching the required intent
 * and handling the results.
 *
 * Once result is available, the derived class can perform the required action.
 */
abstract class SignUpFormAvatarFragment: Fragment() {

    /**
     * View Model for handling signup flow.
     */
    private lateinit var signUpAvatarViewModel: SignUpAvatarViewModel

    val avatarAbsPath: String?
        get() = signUpAvatarViewModel.getAvatarPath()

    companion object {
        private const val REQUEST_PERMISSION_CODE = 999

        private const val REQUEST_IMAGE_CAPTURE = 11

        private val LOG_TAG = SignUpFormAvatarFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        signUpAvatarViewModel = ViewModelProvider(this).get(SignUpAvatarViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAvatar(signUpAvatarViewModel.getAvatarPath())
    }

    /**
     * Handle the required permission. Checks the status of the permission and handles it accordingly
     */
    protected fun handlePermissionRequests(permissionName: String) {
        val permissionStatus =
            PermissionChecker.checkPermissionStatus(
                activity as Activity,
                permissionName
            ) == PackageManager.PERMISSION_GRANTED

        when {
            permissionStatus -> {
                // Permission is granted, send the intent
                sendAvatarIntent()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity as Activity,
                permissionName
            ) -> {
                // This should be replaced by the custom UI to justify the usage of the permission
                requestPermissions(arrayOf(permissionName), REQUEST_PERMISSION_CODE)
            }
            else -> {
                requestPermissions(arrayOf(permissionName), REQUEST_PERMISSION_CODE)
            }
        }
    }

    /**
     * The function asks the provider for the intent, requests the provider to create file.
     *
     * Once file is returned by the provider, the intent is fired for result.
     *
     * See [onActivityResult] to see handling of the result
     */
    private fun sendAvatarIntent() {
        Toast.makeText(context, "Capture Avatar", Toast.LENGTH_SHORT).show()
        val avatarCaptureIntent = signUpAvatarViewModel.getAvatarIntent()

        avatarCaptureIntent.also { avatarIntent ->
            val avatarFile: File? = try {
                val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                signUpAvatarViewModel.createAvatarFile(storageDir)
            } catch (exception: IOException) {
                null
            }

            avatarFile?.also {
                val avatarURI: Uri = FileProvider.getUriForFile(context!!, getString(R.string.signup_avatar_authority), it)
                avatarCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, avatarURI)
                startActivityForResult(avatarIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty()) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendAvatarIntent()
                } else {
                    // Handle permission denial gracefully
                    Log.i(LOG_TAG, "Permission ${permissions[0]} is denied")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {

            // Image is captured, let's load the avatar and show it on the screen.
            loadAvatar(signUpAvatarViewModel.getAvatarPath())
        }
    }

    protected fun handleAvatarCaptureRequest() {
        val permissionName = signUpAvatarViewModel.getRequiredAvatarPermission()
        if (permissionName.isNullOrEmpty()) {
            // No permission required, continue the work.
            loadAvatar(signUpAvatarViewModel.getAvatarPath())
        } else {
            handlePermissionRequests(permissionName)
        }
    }
    /**
     * Ask the child class to handle the loading of the Avatar once available.
     */
    abstract fun loadAvatar(avatarPath: String?)
}