package com.example.textrecognizer.data

import com.example.textrecognizer.data.model.DataScanned

/*
* Created by Annas Surdyanto on 02/03/2023
*/

interface Repository {
    /**
     * Store the scanned text to Firestore Database
     * @param data is the text value, user installation id, and time saving.
     * @param onSuccess will be invoked once the operation success. It contains document id.
     * @param onFailure will be invoked once the function throw any exceptions. It contains error message.*/
    fun storeData(
        data: HashMap<String, String>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    /**
     * Store the scanned text to Firestore Database
     * @param documentId is the document id to be called in Firestore Database.
     * @param onSuccess will be invoked once the operation success. It contains [DataScanned].
     * @param onFailure will be invoked once the function throw any exceptions. It contains error message.*/
    fun getDataByDocimentId(
        documentId: String,
        onSuccess: (DataScanned) -> Unit,
        onFailure: (String) -> Unit
    )

    /**
     * Store the scanned text to Firestore Database
     * @param userId is the user installation id to be used for querying data.
     * @param onSuccess will be invoked once the operation success. It contains [List] of [DataScanned].
     * @param onFailure will be invoked once the function throw any exceptions. It contains error message.*/
    fun getCollectionByUserId(
        userId: String,
        onSuccess: (List<DataScanned>) -> Unit,
        onFailure: (String) -> Unit
    )

    /**
     * Store the scanned text to Firestore Database
     * @param documentId is the document id of the Firestore Database.
     * @param onSuccess will be invoked once the operation success.
     * @param onFailure will be invoked once the function throw any exceptions. It contains error message.*/
    fun updateData(
        documentId: String, text: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}