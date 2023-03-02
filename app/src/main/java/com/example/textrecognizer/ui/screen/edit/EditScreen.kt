package com.example.textrecognizer.ui.screen.edit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.textrecognizer.R
import com.example.textrecognizer.toast
import com.example.textrecognizer.ui.components.Container
import com.example.textrecognizer.ui.components.CustomTextField
import com.example.textrecognizer.ui.components.PrimaryButton
import com.example.textrecognizer.ui.graph.AppDestination

/**
* Created by Annas Surdyanto on 02/03/2023
* @param documentId is the id of the document to be queried in the Firestore Database.
* @param gate is the gate where this screen is coming from.
* @param onNavigateUp is the unit triggered when the user click back arrow on the top bar
* @param onUpdated is the unit triggered when the user save the text. This is to trigger the [AppDestination.COLLECTION_SCREEN]*
* to refresh the data.
*/
@Composable
fun EditScreen(
    viewModel: EditViewModel = hiltViewModel(),
    documentId: String,
    gate: String,
    onNavigateUp: () -> Unit,
    onUpdated:()->Unit
) {
    val text = viewModel.text
    val context = LocalContext.current
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    //Initialization
    if (viewModel.documentId.value.isEmpty()) viewModel.init(documentId)

    /** Save edited text*/
    val save: () -> Unit = {
        viewModel.update {
            context.toast(R.string.text_saved)
            if (gate == AppDestination.COLLECTION_SCREEN) onUpdated()
        }
    }
    Container(
        label = stringResource(R.string.scan_result),
        leadingIcon = Icons.Default.ArrowBack,
        onLeadingIconClick = onNavigateUp,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onErrorConfirmed = viewModel::resetError
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (textRef, buttonRef) = createRefs()

            //Text Field
            CustomTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .constrainAs(textRef) {
                        top.linkTo(parent.top)
                        bottom.linkTo(buttonRef.top, margin = 8.dp)
                    },
                textState = text
            )

            //Save Button
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(buttonRef) {
                        bottom.linkTo(parent.bottom)
                    },
                label = stringResource(R.string.save),
                onClick = save
            )
        }
    }
}