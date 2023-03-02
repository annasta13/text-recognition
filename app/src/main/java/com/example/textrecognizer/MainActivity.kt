package com.example.textrecognizer

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.textrecognizer.helper.PermissionHelper
import com.example.textrecognizer.helper.PermissionHelper.Companion.REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST
import com.example.textrecognizer.ui.graph.AppGraph
import com.example.textrecognizer.ui.theme.TextRecognizerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val permissionHelper = PermissionHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!permissionHelper.isCameraPermissionGranted(this))
            permissionHelper.requestForegroundPermission(this)
        else runApp()
    }

    private fun runApp() {
        setContent {
            TextRecognizerTheme {
                AppGraph()
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST) {
            val index = permissions.indexOf(CAMERA)
            kotlin.runCatching {
                if (grantResults[index] != PERMISSION_GRANTED) permissionHelper.showDialog(this)
                else runApp()
            }
        }
    }
}