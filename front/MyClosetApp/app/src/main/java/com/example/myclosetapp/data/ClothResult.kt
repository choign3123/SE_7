package com.example.myclosetapp.data

// 개별 옷 조회 시 데이터 클래스
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