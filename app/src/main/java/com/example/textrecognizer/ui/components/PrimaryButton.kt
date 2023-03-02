package com.example.textrecognizer.ui.components

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    label: String,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {
    Button(modifier = modifier, colors = colors, onClick = onClick) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}

@Composable
fun SecondaryButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.SemiBold
            )
        )
    }
}