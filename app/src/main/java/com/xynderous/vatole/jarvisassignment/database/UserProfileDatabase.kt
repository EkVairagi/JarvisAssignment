package com.xynderous.vatole.jarvisassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [UserProfile::class], version = 2)
abstract class UserProfileDatabase: RoomDatabase() {

    abstract fun userProfileDao() : UserProfileDAO

    companion object {
        @Volatile
        private var INSTANCE: UserProfileDatabase? = null

        fun getDataBase(context: Context): UserProfileDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserProfileDatabase::class.java,
                    "user_profile_db").fallbackToDestructiveMigration()

                    .build()

                INSTANCE = instance

                instance
            }
        }

    }
}