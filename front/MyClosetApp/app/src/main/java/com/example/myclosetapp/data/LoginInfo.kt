package com.example.myclosetapp.data

data class LoginInfo(
    var id: String?,
    var password:	String?,
    )

data class LoginResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result: LoginObject
)

data class LoginObject(
    var userIdx: Int?
)