package com.equinoxlab.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.equinoxlab.data.local.entities.Employees
import com.equinoxlab.data.remote.model.ApiResponse
import com.equinoxlab.data.repo.Repository

class MainActivityViewModel(private val repo: Repository) : ViewModel() {

    var remoteRecords: LiveData<ApiResponse>? = null
    var employees: LiveData<List<Employees>>? = null

    fun getRemoteEmployees(): LiveData<ApiResponse> {
        if (remoteRecords == null) {
            remoteRecords = repo.getRemoteEmployees()
        }
        return remoteRecords!!
    }

    suspend fun getAllEmployees(): LiveData<List<Employees>> {
        if (employees == null) {
            try {
                employees = repo.getAllEmployees()
            } catch (e: Exception) {
                println(e)
            }
        }
        return employees!!
    }


}