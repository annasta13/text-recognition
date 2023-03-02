package com.example.textrecognizer.ui.screen.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import com.example.textrecognizer.R
import com.example.textrecognizer.ui.components.Container
import com.example.textrecognizer.ui.components.NoDataView
import com.example.textrecognizer.ui.graph.AppDestination
import com.example.textrecognizer.ui.graph.AppDestination.COLLECTION_SCREEN
import com.example.textrecognizer.ui.graph.AppDestination.EDIT_SCREEN
import com.example.textrecognizer.util.Constants

/**
 * Created by Annas Surdyanto on 02/03/2023
 * @param savedState is the [SavedStateHandle] to identify whether this screen should refresh the data.
 * @param onNavigateUp is the unit triggered when the user click back arrow on the top bar
 * @param onNavigate is the unit triggered when the user click an item to navigate to [AppDestination.EDIT_SCREEN]*
 * to refresh the data.*/
@Composable
fun CollectionScreen(
    viewModel: CollectionViewModel = hiltViewModel(),
    savedState: SavedStateHandle?,
    onNavigateUp: () -> Unit,
    onNavigate: (String) -> Unit
) {
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val list by viewModel.list

    val shouldRefresh = savedState?.get<Boolean>(Constants.shouldRefresh) ?: false
    if (shouldRefresh) {
        viewModel.getCollections()
        savedState?.remove<Boolean>(Constants.shouldRefresh)
    }

    Container(
        label = stringResource(id = R.string.collection),
        isLoading = isLoading,
        errorMessage = errorMessage,
        leadingIcon = Icons.Default.ArrowBack,
        onLeadingIconClick = onNavigateUp,
        onErrorConfirmed = { viewModel.resetError().also { onNavigateUp() } }
    ) { padding ->
        if (list.isEmpty()) NoDataView(modifier = Modifier.padding(padding))
        else LazyColumn(
            modifier = Modifier.padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(list) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier
                            .clickable { onNavigate("$EDIT_SCREEN/${it.id}/$COLLECTION_SCREEN") }
                            .padding(8.dp)
                    ) {
                        Text(text = it.text, overflow = TextOverflow.Ellipsis, maxLines = 2)
                    }
                }
            }
        }
    }
}