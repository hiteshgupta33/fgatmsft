package com.fgmsft.signmeup.signup.permission

import android.app.Activity
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat

/**
 * Checks the status of the permission as given by the user.
 *
 * Uses [ContextCompat.checkSelfPermission] for checking the current status
 */
object PermissionChecker {

    /**
     * Check the permission status of the given permission
     *
     * @param activity
     * @param permissionName Name of the permission for which status has to be checked.
     */
    fun checkPermissionStatus(@NonNull activity: Activity, @NonNull permissionName: String) : Int {

        return ContextCompat.checkSelfPermission(activity, permissionName)
    }
}