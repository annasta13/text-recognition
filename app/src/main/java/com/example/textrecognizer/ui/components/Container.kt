package com.example.textrecognizer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@Composable
fun Container(
    label: String,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isLoading: Boolean = false,
    errorMessage: String? = null,
    onErrorConfirmed: (() -> Unit)? = null,
    onLeadingIconClick: (() -> Unit)? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val isShowingDialog = remember { mutableStateOf(false) }
    errorMessage?.let {
        isShowingDialog.value = true
    }

    if (isShowingDialog.value) errorMessage?.let {
        OkayErrorDialog(
            message = it,
            isShowDialog = isShowingDialog,
            onConfirmed = onErrorConfirmed
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(
                title = label,
                leadingIcon = leadingIcon,
                onLeadingIconClick = onLeadingIconClick,
                trailingIcon = trailingIcon,
                onTrailingIconClick = onTrailingIconClick
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            content(padding)
            if (isLoading) Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray.copy(alpha = 0.3f))
                    .clickable(enabled = false, onClick = {}),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        }
    }
}