package com.example.textrecognizer.ui.screen.main

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
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val scannedText = mutableStateOf<String?>(null)
    private val userId = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    init {
        initUserId()
    }

    private fun initUserId() {
        FirebaseInstallations.getInstance().id.addOnSuccessListener {
            userId.value = it
        }
    }

    fun resetError(){
        errorMessage.value = null
    }

    private fun handleFailure(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }


    fun upload(onSuccess: (String) -> Unit) {
        isLoading.value = true
        when {
            scannedText.value == null -> handleFailure("Text should not be empty.")
            userId.value.isEmpty() -> { //user id not generated yet
                initUserId()
                handleFailure("Unable to generated installation id. Please retry later")
            }
            else -> storeScanningData { documentId ->
                isLoading.value = false
                onSuccess(documentId)
            }
        }
    }

    private fun storeScanningData(onSuccess: (String) -> Unit) {
        val data = hashMapOf(
            DataScanned.user_id_key to userId.value,
            DataScanned.text_key to scannedText.value!!
        )
        repository.storeData(
            data = data,
            onSuccess = onSuccess,
            onFailure = ::handleFailure
        )
    }

    fun resetState() {
        scannedText.value = null
    }
}