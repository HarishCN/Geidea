package com.geidea.data.api

import com.geidea.modelNew.userdetails.UserDetails
import com.pinterestimageload.modelNew.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users?per_page=20")
    suspend fun getUsers(): ResponseModel

    @GET("users/{id}")
    suspend fun getUsersDetails(@Path("id") userId: Int?): UserDetails


}