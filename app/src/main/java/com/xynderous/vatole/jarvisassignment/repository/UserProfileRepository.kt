package com.xynderous.vatole.jarvisassignment.repository
import com.xynderous.vatole.jarvisassignment.database.UserProfile
import com.xynderous.vatole.jarvisassignment.database.UserProfileDAO
import kotlinx.coroutines.flow.Flow


class UserProfileRepository (private val userProfileDAO: UserProfileDAO) {


    fun submitUser(userProfile: UserProfile) =  userProfileDAO.submitUser(userProfile)

    fun updateUser(userProfile: UserProfile) = userProfileDAO.updateUser(userProfile)

    suspend fun deleteUser(userProfile: UserProfile) = userProfileDAO.deleteUser(userProfile)

    fun getAllUser(): Flow<List<UserProfile>> =  userProfileDAO.getAll()
    
}