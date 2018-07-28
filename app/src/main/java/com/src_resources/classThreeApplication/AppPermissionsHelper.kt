package com.src_resources.classThreeApplication

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.AndroidRuntimeException

enum class PermissionRequestCode(val intValue: Int) {
    REQUEST_READ_EXTERNAL_STORAGE(1),
    REQUEST_WRITE_EXTERNAL_STORAGE(2);
}

fun Context.checkSelfPermissionCompat(permissionString: String): Int {
    return ContextCompat.checkSelfPermission(this, permissionString)
}

fun Activity.requestPermissionCompat(requestCode: Int, permissionString: String): Int {
    ActivityCompat.requestPermissions(this, arrayOf(permissionString), requestCode)
    return checkSelfPermissionCompat(permissionString)
}

fun Activity.requestPermissionIfNotGrantedCompat(requestCode: Int,  permissionString: String): Int {
    if (checkSelfPermissionCompat(permissionString) == PackageManager.PERMISSION_DENIED) {
        requestPermissionCompat(requestCode, permissionString)
    }

    return checkSelfPermissionCompat(permissionString)
}
