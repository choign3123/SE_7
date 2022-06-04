package com.example.cameraandgallery

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {
    /*

    @POST("/users/signup")
    fun postSignUpInfo(
        @Body jsonparams: SignUpInfo
    ): Call<SignUpResult>

    @POST("/users/login")
    fun postLoginInfo(
        @Body jsonparams: LoginInfo
    ): Call<LoginResult>


    @GET("/users/{userIdx}")
    fun getUserInfo(
        @Path("userIdx") userIdx : Int?
    ): Call<UserInfoResult>

     */

    @GET("/clths/info/all/{userIdx}")
    fun getAllCloth(
        @Path("userIdx") userIdx : Int?
    ): Call<AllClothResult>

    @GET("/clths/info/{userIdx}")
    fun getCloth(
        @Path("userIdx") userIdx: Int?,
        @Query("clthIdx") clthIdx: Int?
    ): Call<ClothResult>

    /*

    @GET("/clths/bookmark/{userIdx}")
    fun getBookmark(
        @Path("userIdx") userIdx: Int?
    ): Call<BookmarkResult>

     */

    @POST("/clths/{userIdx}")
    fun postCloth(
        @Path("userIdx") userIdx: Int?,
        @Body jsonparams: ClothInfo
    ): Call<PostClothResult>



    companion object {
        private const val BASE_URL = "https://hana-umc.shop"

        fun create(): RetrofitService {

            val gson : Gson =   GsonBuilder().setLenient().create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(RetrofitService::class.java)
        }
    }
}