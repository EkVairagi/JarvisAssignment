package com.xynderous.vatole.jarvisassignment.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user_profiles")
data class UserProfile(

    @PrimaryKey(autoGenerate = true) val id: Int,
    @NonNull @ColumnInfo(defaultValue = "no first name") val fname: String,
    @NonNull @ColumnInfo(defaultValue = "no last name") val lname: String,
    @NonNull @ColumnInfo(defaultValue = "no dob") val dob: String,
    @NonNull @ColumnInfo(defaultValue = "no mobile") val mobile: String,

    @NonNull @ColumnInfo(defaultValue = "no house no") val houseno: String,
    @NonNull @ColumnInfo(defaultValue = "no area") val area: String,
    @NonNull @ColumnInfo(defaultValue = "no pincode") val pincode: String,
    @NonNull @ColumnInfo(defaultValue = "no city") val city: String,
    @NonNull @ColumnInfo(defaultValue = "no state") val state: String,

    @NonNull @ColumnInfo(defaultValue = "no level") val level: String,
    @NonNull @ColumnInfo(defaultValue = "no stream") val stream: String,
    @NonNull @ColumnInfo(defaultValue = "no start year") val start_year: String,
    @NonNull @ColumnInfo(defaultValue = "no end year") val end_year: String,
    @NonNull @ColumnInfo(defaultValue = "no college") val college_name: String,


    @NonNull @ColumnInfo(defaultValue = "no imageUrl")val imageUrl: String

): Serializable
