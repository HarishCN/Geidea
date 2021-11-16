package com.geidea.modelNew.userdetails

import com.google.gson.annotations.SerializedName

   
data class UserDetails (

   @SerializedName("data") var data : Data,
   @SerializedName("support") var support : Support

)