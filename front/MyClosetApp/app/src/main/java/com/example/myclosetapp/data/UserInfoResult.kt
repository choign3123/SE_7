package com.example.myclosetapp.data

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


