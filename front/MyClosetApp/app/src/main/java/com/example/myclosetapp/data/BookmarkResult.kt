package com.example.myclosetapp.data

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