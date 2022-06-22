package com.example.myclosetapp.data

// 옷 삭제 시 데이터 클래스
data class DeleteResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result:	String
)

