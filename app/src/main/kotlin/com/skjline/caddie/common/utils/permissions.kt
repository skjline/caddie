package com.skjline.caddie.common.utils

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

/**
 * Created on 8/31/17.
 */
fun AppCompatActivity.hasPermission(activity: AppCompatActivity, permission: String) =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.requestPermission(activity: AppCompatActivity, requestCode: Int, permission: String) {
    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
        return
    }

    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }
}