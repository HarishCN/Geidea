package com.geidea.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geidea.data.api.ApiHelper
import com.geidea.data.local.DatabaseHelper
import com.geidea.data.local.entity.User
import com.geidea.utils.Connectivity
import com.geidea.utils.Resource

import kotlinx.coroutines.launch

class UserListViewModel(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    fun fetchUsers(applicationContext: Context) {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            try {
                if (Connectivity.checkForInternet(applicationContext)) {
                    val usersFromApi = apiHelper.getUsers()
                    val usersToInsertInDB = mutableListOf<User>()

                    for (apiUser in usersFromApi.data) {
                        val user = User(
                            apiUser.id,
                            apiUser.email,
                            apiUser.first_name,
                            apiUser.last_name,
                            apiUser.avatar
                        )
                        usersToInsertInDB.add(user)
                    }

                    dbHelper.insertAll(usersToInsertInDB)

                    users.postValue(Resource.success(usersToInsertInDB))
                } else {
                    val usersFromDb = dbHelper.getUsers()
                    users.postValue(Resource.success(usersFromDb))
                }
            } catch (e: Exception) {
                users.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }

}