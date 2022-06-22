package com.example.myclosetapp.data

// 사용자 정보 조회 시 데이터 클래스
data class UserInfoResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result: UserInfoObject
)

data class  UserInfoObject(
    var id:	String,
    var name:	String,
    var numOfClth:	Int

)


