package com.example.myclosetapp

import com.example.myclosetapp.data.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {

    // 회원가입
    @POST("/users/signup")
    fun postSignUpInfo(
        @Body jsonparams: SignUpInfo
    ): Call<SignUpResult>

    // 로그인
    @POST("/users/login")
    fun postLoginInfo(
        @Body jsonparams: LoginInfo
    ): Call<LoginResult>

    // 회원정보 조회
    @GET("/users/{userIdx}")
    fun getUserInfo(
        @Path("userIdx") userIdx : Int?
    ): Call<UserInfoResult>

    // 회원탈퇴
    @DELETE("/users/deleteUser/{userIdx}")
    fun deleteUser(
        @Path("userIdx") userIdx: Int?
    ): Call<DeleteUserResult>

    // 옷 등록
    @POST("/clths/{userIdx}")
    fun postCloth(
        @Path("userIdx") userIdx: Int?,
        @Body jsonparams: ClothInfo
    ): Call<PostClothResult>

    // 모든 옷 정보 조회
    @GET("/clths/info/all/{userIdx}")
    fun getAllCloth(
        @Path("userIdx") userIdx : Int?
    ): Call<AllClothResult>

    // 개별 옷 정보 조회
    @GET("/clths/info/{userIdx}")
    fun getCloth(
        @Path("userIdx") userIdx: Int?,
        @Query("clthIdx") clthIdx: Int?
    ): Call<ClothResult>

    // response body 변경?
    // 즐겨찾기 조회
    @GET("/clths/bookmark/{userIdx}")
    fun getBookmark(
        @Path("userIdx") userIdx: Int?
    ): Call<AllClothResult>

    // 옷 삭제
    @DELETE("/clths/{userIdx}")
    fun deleteCloth(
        @Path("userIdx") userIdx: Int?,
        @Query("clthIdx") clthIdx: Int?
    ): Call<DeleteResult>

    // 옷 수정
    @PATCH("/clths/{userIdx}")
    fun modifyCloth(
        @Path("userIdx") userIdx: Int?,
        @Query("clthIdx") clthIdx: Int?,
        @Body jsonparams: ModifyInfo
    ): Call<ModifyResult>

    // 옷 검색
    @GET ("/clths/search/{userIdx}")
    fun searchCloth(
        @Path("userIdx") userIdx: Int?,
        @Query("season") season: String?,
        @Query("category") category: String?,
//        @Query("bm") bm: Boolean?
    ): Call<AllClothResult>


    // 싱글톤
    companion object {
        private const val BASE_URL = "https://hana-umc.shop"

        fun create(): RetrofitService {

        val gson :Gson =   GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RetrofitService::class.java)
        }
    }
}