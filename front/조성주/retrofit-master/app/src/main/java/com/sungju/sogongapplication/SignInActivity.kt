package com.sungju.sogongapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SignInActivity : AppCompatActivity() {

    lateinit var id:EditText     //아이디 edittext
    lateinit var password:EditText   //비번  edittext


    lateinit var btnsignin: Button     //로그인 버튼
    lateinit var btnsignup: Button    //회원가입 버튼



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)


        //연결
        id = findViewById(R.id.textId)
        password = findViewById(R.id.textPassword)


        btnsignin = findViewById(R.id.btnsignin)    //로그인 버튼
        btnsignup = findViewById((R.id.btnsignup))  //회원가입 버튼


        //레트로핏 만들기
        val retrofit = Retrofit.Builder()
            .baseUrl("https://hana-umc.shop") //서버주소
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(LoginService::class.java)


        //로그인 버튼을 클릭하면 아이디랑 비번을 가지고 레트로핏 통신을 해서 로그인 처리

        btnsignin.setOnClickListener {

            val idStr = id.text.toString()
            val pwStr = password.text.toString()
            service.postLoginInfo(idStr, pwStr).enqueue(object :Callback<LoginResult> {
                override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                    val result = response.body()
                    Log.d("로그인", "${result}")
                }

                override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                    Log.d("로그인", "${t.localizedMessage}")
                }
            })
        }

        btnsignup.setOnClickListener {
            Intent(this,SignUpActivity::class.java).run{
                startActivity(this)
            }
        }
    }

}

interface LoginService {
    @FormUrlEncoded
    @POST("/users/login")
    fun postLoginInfo(@Field("id")id: String, @Field("password")password: String) :Call<LoginResult>


}