package com.example.textrecognizer.di

import android.content.Context
import com.example.textrecognizer.App
import com.example.textrecognizer.data.Repository
import com.example.textrecognizer.data.RepositoryImpl
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* Created by Annas Surdyanto on 02/03/2023
*/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): App {
        FirebaseApp.initializeApp(app.applicationContext)
        return app as App
    }

    @Singleton
    @Provides
    fun provideDatabase(app: App): FirebaseFirestore {
        FirebaseApp.initializeApp(app.applicationContext)
        return FirebaseFirestore.getInstance()
    }


    @Singleton
    @Provides
    fun provideRepository(database: FirebaseFirestore): Repository {
        return RepositoryImpl(database)
    }
}
