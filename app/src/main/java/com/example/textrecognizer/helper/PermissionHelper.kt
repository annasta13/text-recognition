package com.example.textrecognizer.helper

import android.Manifest.permission.CAMERA
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.textrecognizer.R
import com.example.textrecognizer.openSetting

/*
* Created by Annas Surdyanto on 02/03/2023
*/

class PermissionHelper {

    fun isCameraPermissionGranted(context: Context): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
            context, CAMERA
        )
    }

    fun requestPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(CAMERA),
            REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST
        )
    }

    fun requestForegroundPermission(activity: Activity) {
        val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA)
        if (showRationale) showDialog(activity)
        else requestPermission(activity)
    }

    fun showDialog(activity: Activity) {
        val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, CAMERA)
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.apply {
            setTitle(context.getString(R.string.warning))
            setMessage(context.getString(R.string.permission_message))
            setNegativeButton(
                context.getString(R.string.exit)
            ) { dialog, _ ->
                dialog.dismiss()
                activity.finish()
            }
            setPositiveButton(context.getString(R.string.grant)) { dialog, _ ->
                dialog.dismiss()
                if (showRationale) requestPermission(activity)
                else {
                    context.openSetting()
                    activity.finish()
                }
            }
        }
        dialogBuilder.show()
    }

    companion object {
        const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST = 123
    }
}