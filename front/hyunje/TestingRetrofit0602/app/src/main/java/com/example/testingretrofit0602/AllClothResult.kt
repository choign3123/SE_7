package com.example.testingretrofit0602

import java.util.ArrayList

data class AllClothResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

//    var result:	List<AllClothObject>
    var result : ArrayList<AllClothObject>
)

data class  AllClothObject(
    var clthIdx:	Int,
    var clthImgUrl:	String

)


