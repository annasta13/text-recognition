package com.example.textrecognizer.data

import com.example.textrecognizer.asDataScanned
import com.example.textrecognizer.data.model.DataScanned
import com.example.textrecognizer.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

class RepositoryImpl(private val database: FirebaseFirestore) : Repository {

    override fun storeData(
        data: HashMap<String, String>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.collection(Constants.collectionName)
            .add(data)
            .addOnSuccessListener { documentReference ->
                onSuccess(documentReference.id)
                Timber.d("check document added with ID: ${documentReference.id}")
                getDataByDocimentId(documentReference.id, onSuccess = {}, onFailure = {})
            }
            .addOnFailureListener { e ->
                onFailure(e.message.toString())
                Timber.w("check error storing document", e)
            }
    }

    override fun getDataByDocimentId(
        documentId: String,
        onSuccess: (DataScanned) -> Unit,
        onFailure: (String) -> Unit
    ) {
        kotlin.runCatching {
            database.collection(Constants.collectionName)
                .get()
                .addOnSuccessListener { result ->
                    result.forEach { document ->
                        if (document.id == documentId) onSuccess(document.asDataScanned())
                        Timber.d("check doesUserExists ${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception.message.toString())
                    Timber.w("check Error getting documents.", exception)
                }
        }.onFailure { exception -> onFailure(exception.message.toString()) }
    }

    override fun getCollectionByUserId(
        userId: String,
        onSuccess: (List<DataScanned>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        kotlin.runCatching {
            database.collection(Constants.collectionName)
                .get()
                .addOnSuccessListener { result ->
                    val collections = mutableListOf<DataScanned>()
                    result.forEach { document ->
                        if (document["user_id"] as String == userId) collections.add(document.asDataScanned())
                    }
                    onSuccess(collections.toList())
                }
                .addOnFailureListener { exception ->
                    onFailure(exception.message.toString())
                    Timber.w("check Error getting documents.", exception)
                }
        }.onFailure { exception -> onFailure(exception.message.toString()) }
    }

    override fun updateData(
        documentId: String, text: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        val data = mapOf(DataScanned.text_key to text)
        database.collection(Constants.collectionName).document(documentId).update(data)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message.toString()) }
    }
}