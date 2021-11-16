package com.geidea.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.geidea.data.api.ApiHelper
import com.geidea.data.local.DatabaseHelper
import com.geidea.ui.UserListViewModel
import com.geidea.ui.userdetails.UserDetailsViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            return UserListViewModel(apiHelper, dbHelper) as T
        }

        if (modelClass.isAssignableFrom(UserDetailsViewModel::class.java)) {
            return UserDetailsViewModel(apiHelper, dbHelper) as T
        }


        throw IllegalArgumentException("Unknown class name")
    }

}