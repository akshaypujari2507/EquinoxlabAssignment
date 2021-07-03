package com.equinoxlab.data.local.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Employees", indices = [Index(value = ["email_id"], unique = true)])
class Employees {
//    var TODAYS_COUNT: String? = null;
    var age: String? = null;
//    var created_on: String? = null;
    var dept_id: String? = null;
    var dept_name: String? = null;
    var email_id: String? = null;
    var gender: String? = null;
//    var is_deleted: Boolean? = null;
    var mobile: String? = null;
    var name: String? = null;
//    var password: String? = null;
//    var profile_img: String? = null;
//    var reporting_to_id: String? = null;
//    var reporting_to_name: String? = null;
//    var role_id: String? = null;
//    var role_name: String? = null;
//    var token: String? = null;
//    var updated_on: String? = null;

    @NonNull
    @PrimaryKey(autoGenerate = false)
    var user_id: String? = null;


}