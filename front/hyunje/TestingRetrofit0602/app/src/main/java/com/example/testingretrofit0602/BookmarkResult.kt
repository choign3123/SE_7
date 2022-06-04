package com.example.testingretrofit0602

data class BookmarkResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result:	ArrayList<BookmarkObject>


)

data class BookmarkObject(
    var clthIdx:	Int,
    var clthImgUrl: 	String
)