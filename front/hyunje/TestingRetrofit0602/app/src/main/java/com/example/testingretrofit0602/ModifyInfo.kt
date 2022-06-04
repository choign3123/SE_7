package com.example.testingretrofit0602

data class ModifyInfo(
    var bookmark:	Boolean,
    var category:	String,
    var season:	String
)

data class ModifyResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result:	String
)

