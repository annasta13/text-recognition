package com.example.textrecognizer.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.textrecognizer.R

/**
 * Created by Annas Surdyanto on 02/03/23.
 *
 */

@Composable
fun DialogScreen(
    title: String,
    message: String,
    positiveText: String = stringResource(R.string.ok),
    negativeText: String = stringResource(R.string.cancel),
    onConfirmed: (() -> Unit?)? = null,
    onCancel: (() -> Unit?)? = null,
    isShowDialog: MutableState<Boolean>
) {
    if (isShowDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                PrimaryButton(
                    label = positiveText,
                    onClick = { onConfirmed?.let { it() } }
                )
            },
            dismissButton =
            {
                SecondaryButton(onClick = {
                    isShowDialog.value = false
                    onCancel?.let { it() }
                }, label = negativeText)
            }
        )
    }
}

@Composable
fun OptionDialog(
    title: String = stringResource(R.string.error_title),
    positiveText: String = stringResource(R.string.retry),
    negativeText: String = stringResource(R.string.cancel),
    message: String,
    onConfirmed: () -> Unit,
    onCancel: () -> Unit,
    isShowDialog: MutableState<Boolean>
) {
    DialogScreen(
        title = title,
        message = message,
        positiveText = positiveText,
        negativeText = negativeText,
        onConfirmed = onConfirmed,
        isShowDialog = isShowDialog,
        onCancel = onCancel
    )
}

@Composable
fun RetryDialog(
    title: String = stringResource(R.string.error_title),
    message: String,
    onConfirmed: (() -> Unit?)? = null,
    onCancel: (() -> Unit?)? = null,
    isShowDialog: MutableState<Boolean>
) {
    DialogScreen(
        title = title,
        message = message,
        positiveText = stringResource(R.string.retry),
        negativeText = stringResource(R.string.cancel),
        onConfirmed = onConfirmed,
        isShowDialog = isShowDialog,
        onCancel = onCancel
    )
}

@Composable
fun OkayErrorDialog(
    message: String,
    isShowDialog: MutableState<Boolean>,
    positiveText: String = "Ok",
    onConfirmed: (() -> Unit?)? = null
) {
    if (isShowDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = "Oups, error occurred") },
            text = { Text(text = message) },
            confirmButton = {
                PrimaryButton(
                    label = positiveText,
                    onClick = {
                        onConfirmed?.let { it() }
                        isShowDialog.value = false
                    }
                )
            }
        )
    }
}

@Composable
fun OkaySuccessDialog(
    title: String = "Great",
    message: String,
    isShowDialog: MutableState<Boolean>,
    positiveText: String = "Ok",
    onConfirmed: (() -> Unit?)? = null
) {
    if (isShowDialog.value) {
        AlertDialog(
            onDismissRequest = { isShowDialog.value = false },
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                PrimaryButton(
                    label = positiveText,
                    onClick = {
                        onConfirmed?.let { it() }
                        isShowDialog.value = false
                    }
                )
            }
        )
    }
}

@Composable
@Preview
fun DialogScreenPreview() {
    DialogScreen(
        title = "Error",
        message = "An error occurred. Retry?",
        positiveText = "Yes",
        negativeText = "No",
        onConfirmed = { },
        onCancel = {},
        remember { mutableStateOf(false) }
    )
}