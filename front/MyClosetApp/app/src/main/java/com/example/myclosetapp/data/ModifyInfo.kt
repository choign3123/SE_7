package com.example.myclosetapp.data

// 옷 수정 시 데이터 클래스
data class ModifyInfo(
    var bookmark:	Boolean?,
    var category:	String?,
    var season:	String?
)

data class ModifyResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result:	String
)

