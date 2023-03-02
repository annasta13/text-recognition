package com.example.textrecognizer.ui.screen.main

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.TakePicturePreview
import androidx.activity.result.launch
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.textrecognizer.R
import com.example.textrecognizer.helper.PermissionHelper
import com.example.textrecognizer.openSetting
import com.example.textrecognizer.toast
import com.example.textrecognizer.ui.components.Container
import com.example.textrecognizer.ui.graph.AppDestination
import com.example.textrecognizer.ui.graph.AppDestination.EDIT_SCREEN
import com.example.textrecognizer.ui.graph.AppDestination.MAIN_SCREEN
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions.DEFAULT_OPTIONS


/**
 * Created by Annas Surdyanto on 02/03/2023
 * @param onNavigate is function to navigate to particular screen
 */

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), onNavigate: (String) -> Unit) {
    val context = LocalContext.current

    /** Permission helper to check camera permission*/
    val permission = remember { PermissionHelper() }

    /** Scanned text value*/
    val scannedText = viewModel.scannedText

    /** Text recognizer to operate scanning*/
    val textRecognizer = remember { TextRecognition.getClient(DEFAULT_OPTIONS) }

    /** Unit to handle when failure occurs*/
    val onFailure: (Throwable) -> Unit = { context.toast(it.message.toString()) }

    /** Loading state*/
    val isLoading by viewModel.isLoading

    /** Error message state*/
    val errorMessage by viewModel.errorMessage

    /** Launcher of camera*/
    val launcher = rememberLauncherForActivityResult(contract = TakePicturePreview()) { result ->
        runCatching {
            result?.let { bitmap ->
                val image = InputImage.fromBitmap(bitmap, 0)
                textRecognizer.process(image)
                    .addOnSuccessListener { scannedText.value = it.text }
                    .addOnFailureListener(onFailure)
            }
        }.onFailure(onFailure)
    }

    /** Unit to start camera*/
    val launchCamera: () -> Unit = {
        if (permission.isCameraPermissionGranted(context)) launcher.launch()
        else context.openSetting()
    }

    /** Unit to upload the scanned result.*/
    val upload: () -> Unit = {
        viewModel.upload(onSuccess = {
            viewModel.resetState()
            onNavigate("$EDIT_SCREEN/$it/$MAIN_SCREEN") })
    }

    Container(
        label = stringResource(id = R.string.app_name),
        isLoading = isLoading,
        errorMessage = errorMessage,
        onErrorConfirmed = viewModel::resetError,
        trailingIcon = Icons.Default.List,
        onTrailingIconClick = { onNavigate(AppDestination.COLLECTION_SCREEN) }
    ) { padding ->
        if (scannedText.value != null) {
            ScanResultView(
                modifier = Modifier.padding(padding),
                text = scannedText.value!!,
                onUpload = upload,
                onRetake = launchCamera
            )
        } else StandByView(
            modifier = Modifier.padding(padding),
            onTakePicture = launchCamera
        )
    }
}