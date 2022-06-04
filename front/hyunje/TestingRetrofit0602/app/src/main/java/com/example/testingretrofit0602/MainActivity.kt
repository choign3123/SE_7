package com.example.testingretrofit0602

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.FileProvider
import com.example.testingretrofit0602.databinding.ActivityMainBinding
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.internal.Streams.parse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.security.KeyStore
import java.util.logging.Level.parse
import org.json.JSONObject as JSONObject

class MainActivity : AppCompatActivity() {


    val retro = RetrofitService.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

//        var id: String? = null
//        var pw: String? = null
//
//
//        val data = SignUpInfo("hyun0916", "hyun", "0916hyun", "0916hyun")
//
//        id = "hyun0916"
//        pw = "0916hyun"
//        val data2 = LoginInfo(id, pw)

        var data3: ModifyInfo = ModifyInfo(true,"셔츠","봄")

//        retro.deleteCloth(3,2222).enqueue(object : Callback<DeleteResult>{
//            override fun onResponse(call: Call<DeleteResult>, response: Response<DeleteResult>) {
//                Log.d("myTAG",response.body()?.isSuccess.toString())
//                Log.d("myTAG",response.body().toString())
//
//            }
//
//            override fun onFailure(call: Call<DeleteResult>, t: Throwable) {
//                Log.d("myTAG",t.message.toString())
//                Log.d("myTAG","fail")
//            }
//
//        })

        retro.modifyCloth(3,29,data3).enqueue(object: Callback<ModifyResult>{
            override fun onResponse(call: Call<ModifyResult>, response: Response<ModifyResult>) {
//                Log.d("myTAG",response.body()?.isSuccess.toString())
//                Log.d("myTAG",response.body().toString())
                Log.d("ATAG","왜 또 시발 안되")
                Log.d("ATAG",response.body()?.code.toString())

            }

            override fun onFailure(call: Call<ModifyResult>, t: Throwable) {
//                Log.d("myTAG",t.message.toString())
                Log.d("BTAG","fail")
            }

        })





        /*---------------------------------------------------------------------
        // 회원가입
//        retro.postSignUpInfo(data).enqueue(object: Callback<SignUpResult> {
//            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
//                Log.d("log",response.toString())
//                Log.d("log", response.body().toString())
//                binding.textView.text = response.body()?.isSuccess.toString()
//                binding.textView2.text = response.body()?.message
//            }
//
//            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
//                Log.d("log",t.message.toString())
//                Log.d("log","fail")
//            }
//        })
//
        //--- 로그인 ----------------------------------------
        retro.postLoginInfo(data2).enqueue(object: Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                Log.d("mytag", "1  "+ response.body().toString())
//                binding.textView.text = response.body()?.isSuccess.toString()
//                binding.textView2.text = response.body()?.message
                var userIdx = response.body()?.result!!.userIdx

                //--- 회원정보조회 -----------------------
                retro.getUserInfo(userIdx).enqueue(object: Callback<UserInfoResult>{
                    override fun onResponse(call: Call<UserInfoResult>, response: Response<UserInfoResult>) {
                        Log.d("mytag","2  "+response.body().toString())
//                        binding.textView.text = response.body()?.isSuccess.toString()
//                        binding.textView2.text = response.body()?.result.toString()

                    }

                    override fun onFailure(call: Call<UserInfoResult>, t: Throwable) {
                        Log.d("mytag",t.message.toString())
                        Log.d("mytag","fail")
                    }

                })

                retro.getAllCloth(userIdx).enqueue(object: Callback<AllClothResult>{
                    override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {
                        Log.d("mytag","3  "+response.body().toString())
                        //binding.textView.text = response.body()!!.result[0].clthImgUrl.toString()
                        var clthIdx = response.body()!!.result[0].clthIdx



                        retro.getCloth(userIdx,clthIdx).enqueue(object: Callback<ClothResult>{
                            override fun onResponse(call: Call<ClothResult>, response: Response<ClothResult>) {
                                Log.d("mytag","4  "+response.body().toString())
                                //binding.textView.text = response.body()!!.result.category.toString()

                            }

                            override fun onFailure(call: Call<ClothResult>, t: Throwable) {
                                Log.d("mytag", t.message.toString())
                                Log.d("mytag","fail")
                            }

                        })

                    }

                    override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                        Log.d("mytag",t.message.toString())
                        Log.d("mytag","fail")
                    }
                })
                retro.getBookmark(3).enqueue(object: Callback<BookmarkResult>{
                    override fun onResponse(call: Call<BookmarkResult>, response: Response<BookmarkResult>) {
                        Log.d("mytag","5  "+response.body().toString())
                        binding.textView.text = response.body()!!.result.toString()
                    }

                    override fun onFailure(call: Call<BookmarkResult>, t: Throwable) {
                        Log.d("mytag",t.message.toString())
                        Log.d("mytag","fail")
                    }

                })


            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d("mytag",t.message.toString())
                Log.d("mytag","fail")
            }
        })

-------------------------------------------------------------------*/

    }
}


