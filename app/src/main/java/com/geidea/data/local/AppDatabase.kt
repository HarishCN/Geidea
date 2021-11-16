package com.geidea.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.geidea.data.local.dao.UserDao
import com.geidea.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}