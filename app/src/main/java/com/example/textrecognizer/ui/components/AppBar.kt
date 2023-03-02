package com.example.textrecognizer.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    title: String,
    textStyle: TextStyle = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold),
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    onLeadingIconClick: (() -> Unit)? = null
) {
    TopAppBar(modifier = modifier, backgroundColor = backgroundColor, contentColor = contentColor) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let { startIcon ->
                Icon(
                    modifier = Modifier.clickable { onLeadingIconClick?.invoke() },
                    imageVector = startIcon,
                    contentDescription = title,
                    tint = contentColor
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = textStyle.copy(color = contentColor),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                trailingIcon?.let { endIcon ->
                    Icon(
                        modifier = Modifier.clickable { onTrailingIconClick?.invoke() },
                        imageVector = endIcon,
                        contentDescription = "$title of trailing icon",
                        tint = contentColor
                    )
                }
            }
        }
    }
}