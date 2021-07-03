package com.equinoxlab.data.remote.model

import com.equinoxlab.data.local.entities.Employees
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val CODE: String,
    @SerializedName("DATA") val employees: List<Employees>,
    val MESSAGE: String
)