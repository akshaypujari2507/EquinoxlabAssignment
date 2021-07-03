package com.equinoxlab.data.local.doa

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.equinoxlab.data.local.entities.Employees


@Dao
interface EmployeesDao {

    @Query("SELECT * FROM Employees where user_id = :userid")
    fun getEmployeeByUserID(userid: String): LiveData<Employees>

    @Query("SELECT * FROM Employees")
    fun getAllEmployees(): LiveData<List<Employees>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEmployees(employees: List<Employees>)

}