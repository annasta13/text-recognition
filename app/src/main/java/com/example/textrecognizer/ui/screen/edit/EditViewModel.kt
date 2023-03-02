package com.example.textrecognizer.ui.screen.edit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.textrecognizer.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@HiltViewModel
class EditViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val text = mutableStateOf("")
    val documentId = mutableStateOf("")
    val isLoading = mutableStateOf(true)
    val errorMessage = mutableStateOf<String?>(null)

    fun init(documentId: String) {
        this.documentId.value = documentId
        repository.getDataByDocimentId(
            documentId = documentId,
            onSuccess = { handleSuccess(it.text) },
            onFailure = ::handleFailure
        )
    }

    private fun handleSuccess(data: String) {
        text.value = data
        isLoading.value = false
    }

    private fun handleFailure(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }


    fun resetError() {
        errorMessage.value = null
    }

    fun update(onSuccess: () -> Unit) {
        repository.updateData(
            documentId = documentId.value, text = text.value,
            onSuccess = {
                isLoading.value = false
                onSuccess()
            },
            onFailure = ::handleFailure
        )
    }
}