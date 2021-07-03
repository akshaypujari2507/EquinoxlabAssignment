package com.greenlight.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.equinoxlab.data.local.db.AppDatabase
import com.equinoxlab.data.remote.api.ApiClient
import com.equinoxlab.data.repo.Repository
import com.equinoxlab.ui.viewmodel.factory.ViewModelMainActivityFactory


object Injection {

    var repo: Repository? = null

    //repo provider
    private fun provideRepository(context: Context): Repository {
        val database = AppDatabase.getInstance(context)
        val api = ApiClient.api

        if (repo == null) {
            synchronized(Repository::class.java) {
                if (repo == null) {
                    repo = Repository(database, api)
                }
            }
        }
        return repo!!
    }

    // main activity viewmodel provider
    fun provideMainActivityViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelMainActivityFactory(
            provideRepository(
                context
            )
        )
    }

}