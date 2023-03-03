package com.example.textrecognizer.data

import com.example.textrecognizer.asDataScanned
import com.example.textrecognizer.data.model.DataScanned
import com.example.textrecognizer.util.Constants
import com.google.firebase.firestore.FirebaseFirestore

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
            }
            .addOnFailureListener { e ->
                onFailure(e.message.toString())
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
                    for (document in result) {
                        if (document.id == documentId) onSuccess(document.asDataScanned())
                    }
                }
                .addOnFailureListener { exception ->
                    onFailure(exception.message.toString())
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
                    for (document in result) {
                        if (document["user_id"] as String == userId) collections.add(document.asDataScanned())
                    }
                    onSuccess(collections.toList())
                }
                .addOnFailureListener { exception ->
                    onFailure(exception.message.toString())
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