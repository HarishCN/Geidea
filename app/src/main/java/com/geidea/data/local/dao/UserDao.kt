package com.geidea.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.geidea.data.local.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE id =:id")
    suspend fun getDetails(id: Int?): User

    @Insert
    suspend fun insertAll(users: List<User>)

    @Delete
    suspend fun delete(user: User)

}