package com.geidea.data.api

import com.geidea.modelNew.userdetails.UserDetails
import com.pinterestimageload.modelNew.ResponseModel


interface ApiHelper {

    suspend fun getUsers(): ResponseModel

    suspend fun getUsersDetails(userId: Int?): UserDetails

}