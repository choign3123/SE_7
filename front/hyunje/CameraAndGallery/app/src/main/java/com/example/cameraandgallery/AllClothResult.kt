package com.example.cameraandgallery

import java.util.ArrayList

data class AllClothResult(
    var isSuccess:	Boolean,
    var code:	Int,
    var message:	String,

    var result : ArrayList<AllClothObject>
)

data class  AllClothObject(
    var clthIdx:	Int,
    var clthImgUrl:	String

)


