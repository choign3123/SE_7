package com.example.myclosetapp.data

// 옷 등록 시 데이터 클래스
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
