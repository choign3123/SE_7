package com.example.myclosetapp.data

data class ClothInfo(
    var clthImgUrl:	String?,
    var bookmark:	Boolean?,
    var category:	String?,
    var season:	String?
)

data class PostClothResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result:	String

)
