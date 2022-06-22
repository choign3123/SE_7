package com.example.myclosetapp.data

// 회원가입 시 데이터 클래스
data class SignUpInfo(
    var id: String?,
    var name: String?,
    var password:	String?,
    var pwForCheck:	String?
)

data class SignUpResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result:	String
)