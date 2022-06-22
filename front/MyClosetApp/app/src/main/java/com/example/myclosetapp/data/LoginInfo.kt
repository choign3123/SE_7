package com.example.myclosetapp.data

// 로그인 시 데이터 클래스
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