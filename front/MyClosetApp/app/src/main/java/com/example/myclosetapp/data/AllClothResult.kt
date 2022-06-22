package com.example.myclosetapp.data

// 전체 옷 출력 시 데이터 클래스
data class AllClothResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result : ArrayList<AllClothObject>
)

data class  AllClothObject(
    var clthIdx:	Int,
    var clthImgUrl:	String

)


