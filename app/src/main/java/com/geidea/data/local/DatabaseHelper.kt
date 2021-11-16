package com.geidea.data.local

import com.geidea.data.local.entity.User


interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun getUsersDetails(userId: Int?): User

    suspend fun insertAll(users: List<User>)

}