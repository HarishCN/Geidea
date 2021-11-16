package com.geidea.data.api

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getUsers() = apiService.getUsers()

    override suspend fun getUsersDetails(userId: Int?) = apiService.getUsersDetails(userId)

}