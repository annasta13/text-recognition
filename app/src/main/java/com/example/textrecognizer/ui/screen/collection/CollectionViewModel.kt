package com.example.textrecognizer.ui.screen.collection

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.textrecognizer.data.Repository
import com.example.textrecognizer.data.model.DataScanned
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@HiltViewModel
class CollectionViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val userId = mutableStateOf("")
    val isLoading = mutableStateOf(true)
    val list = mutableStateOf<List<DataScanned>>(emptyList())
    val errorMessage = mutableStateOf<String?>(null)

    init {
        initUserId()
    }

    private fun handleSuccess(data: List<DataScanned>) {
        list.value = data
        isLoading.value = false
    }

    private fun handleFailure(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }

    fun resetError() {
        errorMessage.value = null
    }

    private fun initUserId() {
        FirebaseInstallations.getInstance().id
            .addOnSuccessListener {
                userId.value = it
                getCollections()
            }
            .addOnFailureListener {
                errorMessage.value = it.message.toString()
                isLoading.value = false
            }
    }

    fun getCollections() {
        repository.getCollectionByUserId(
            userId = userId.value,
            onSuccess = ::handleSuccess,
            onFailure = ::handleFailure
        )
    }
}