package com.geidea.ui.userdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geidea.data.api.ApiHelper
import com.geidea.data.local.DatabaseHelper
import com.geidea.data.local.entity.User
import com.geidea.utils.Resource

import kotlinx.coroutines.launch

class UserDetailsViewModel(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper) :
    ViewModel() {

    private val usersDetails = MutableLiveData<Resource<User>>()
    fun fetchUsersDetails(userId: Int?) {
        viewModelScope.launch {
            usersDetails.postValue(Resource.loading(null))
            try {
                // val usersFromDb = dbHelper.getUsersDetails(userId)

                val usersFromApi = apiHelper.getUsersDetails(userId)
                val usersToInsertInDB = mutableListOf<User>()


                val user = User(
                    usersFromApi.data.id,
                    usersFromApi.data.email,
                    usersFromApi.data.firstName,
                    usersFromApi.data.lastName,
                    usersFromApi.data.avatar
                )
                //usersToInsertInDB.add(user)


                //  dbHelper.insertAll(usersToInsertInDB)

                usersDetails.postValue(Resource.success(user))

            } catch (e: Exception) {
                usersDetails.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }





    fun getUsers(): LiveData<Resource<User>> {
        return usersDetails
    }

}