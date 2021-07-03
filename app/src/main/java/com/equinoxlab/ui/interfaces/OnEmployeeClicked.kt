package com.equinoxlab.ui.interfaces

import com.equinoxlab.data.local.entities.Employees

interface OnEmployeeClicked {
    fun onItemClicked(employees: Employees)
}