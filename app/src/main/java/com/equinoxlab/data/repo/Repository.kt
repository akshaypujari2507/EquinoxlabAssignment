package com.equinoxlab.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.equinoxlab.data.local.db.AppDatabase
import com.equinoxlab.data.local.entities.Employees
import com.equinoxlab.data.remote.api.ApiService
import com.equinoxlab.data.remote.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val db: AppDatabase, val api: ApiService) {

    lateinit var employees: LiveData<List<Employees>>
    lateinit var employee: LiveData<Employees>

    fun getRemoteEmployees(): LiveData<ApiResponse> {

        val remoteRecords: MutableLiveData<ApiResponse> = MutableLiveData<ApiResponse>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getManager().execute().body()
            remoteRecords.postValue(response)
            insertRecords(response!!)
        }

        return remoteRecords
    }

    fun insertRecords(response: ApiResponse) {

        val employee = response.employees

        GlobalScope.launch(Dispatchers.IO) {
            db.employeesDao().insertEmployees(employee)
        }

    }

    suspend fun getEmployee(userid: String): LiveData<Employees> {

        withContext(Dispatchers.IO) {
            try {
                employee = db.employeesDao().getEmployeeByUserID(userid)
            } catch (e: Exception) {
                println(e)
            }
        }
        return employee
    }

    suspend fun getAllEmployees(): LiveData<List<Employees>> {

        withContext(Dispatchers.IO) {
            try {
                employees = db.employeesDao().getAllEmployees()
            } catch (e: Exception) {
                println(e)
            }
        }
        return employees
    }

}