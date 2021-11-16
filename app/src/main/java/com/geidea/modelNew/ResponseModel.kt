package com.pinterestimageload.modelNew

import com.geidea.data.local.entity.User


data class ResponseModel (

   var page : Int,
   var perPage : Int,
   var total : Int,
   var totalPages : Int,
   var data : List<User>,
   var support : Support

)