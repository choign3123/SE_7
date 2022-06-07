package com.sungju.sogongapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SignUpActivity : AppCompatActivity() {

    lateinit var id: EditText
    lateinit var name: EditText
    lateinit var password: EditText
    lateinit var pwForCheck: EditText


    lateinit var btnComplete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        id = findViewById(R.id.textId)
        name = findViewById(R.id.textName)
        password = findViewById(R.id.textPassword)
        pwForCheck = findViewById(R.id.textPwForCheck)

        btnComplete = findViewById(R.id.btnComplete)


        val retrofit = Retrofit.Builder()
            .baseUrl("https:hana-umc.shop") //서버주소
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(SignUpService::class.java)

        btnComplete.setOnClickListener{

            val idStr = id.text.toString()
            val nameStr = name.text.toString()
            val pwStr = password.text.toString()
            val pwForCheckStr = pwForCheck.text.toString()


            service.postSignUpInfo(idStr, nameStr, pwStr, pwForCheckStr).enqueue(object : Callback<SignUpResult> {
                override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                    val result = response.body()
                    Log.d("회원가입","${result}")
                }

                override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                    Log.e("회원가입","${t.localizedMessage}")


                }

            })


        }
    }
}

interface SignUpService{
    @FormUrlEncoded
    @POST("/users/signup") //자원의 주소
    fun postSignUpInfo(@Field("id")id: String,
                       @Field("name")name: String,
                       @Field("password")password: String),
                       @Field("pwForCheck")pwForCheck: String): Call<SignUpResult>

}


