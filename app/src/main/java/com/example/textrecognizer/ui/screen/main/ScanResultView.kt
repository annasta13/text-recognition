package com.example.textrecognizer.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.textrecognizer.R
import com.example.textrecognizer.ui.components.PrimaryButton
import com.example.textrecognizer.ui.components.SecondaryButton

/**
 * Created by Annas Surdyanto on 02/03/2023
 * @param onUpload is the unit triggered when the user click the upload button to store data into Firestore Database.
 * @param onRetake is the unit triggered when the user click the retake button to re-open the camera.
 */
@Composable
fun ScanResultView(
    modifier: Modifier = Modifier,
    text: String,
    onUpload: () -> Unit,
    onRetake: () -> Unit
) {
    ConstraintLayout(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        val (textRef, buttonRef) = createRefs()
        Column(
            modifier = Modifier.constrainAs(textRef) {
                top.linkTo(parent.top)
                bottom.linkTo(buttonRef.top, margin = 8.dp)
                height = Dimension.fillToConstraints
            },
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.scan_result),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(buttonRef) {
                    bottom.linkTo(parent.bottom)
                },

            ) {
            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.retake),
                onClick = onRetake
            )
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.upload),
                onClick = onUpload
            )
        }
    }
}