package com.example.textrecognizer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.example.textrecognizer.data.model.DataScanned
import com.google.firebase.firestore.QueryDocumentSnapshot

/*
* Created by Annas Surdyanto on 02/03/2023
*/

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(res: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, getString(res), length).show()
}

fun Context.openSetting() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri: Uri = Uri.fromParts("package", getPackageName(), null)
    intent.data = uri
    startActivity(intent)
}

fun QueryDocumentSnapshot.asDataScanned(): DataScanned {
    return DataScanned(
        id = this.id,
        userId = this[DataScanned.user_id_key] as String,
        text = this[DataScanned.text_key] as String
    )
}