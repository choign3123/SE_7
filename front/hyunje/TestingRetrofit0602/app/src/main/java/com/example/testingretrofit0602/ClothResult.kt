package com.example.testingretrofit0602

data class ClothResult(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: ClothObject
)
data class ClothObject(
    val bookmark: Boolean,
    val category: String,
    val clthImgUrl: String,
    val season: String
)