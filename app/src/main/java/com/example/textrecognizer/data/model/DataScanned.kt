package com.example.textrecognizer.data.model

/*
* Created by Annas Surdyanto on 02/03/2023
*/

data class DataScanned(
    val id: String,
    val text: String,
    val userId: String,
){
    companion object{
        const val user_id_key = "user_id"
        const val text_key = "text"
    }
}