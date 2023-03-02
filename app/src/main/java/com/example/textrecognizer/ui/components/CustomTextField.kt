package com.example.textrecognizer.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

/*
* Created by Annas Surdyanto on 03/03/2023
*/

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    textState: MutableState<String>
) {
    TextField(
        modifier = modifier,
        value = textState.value,
        onValueChange = { textState.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface
        )
    )
}