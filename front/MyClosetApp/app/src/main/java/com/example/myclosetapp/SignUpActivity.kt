package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myclosetapp.data.SignUpInfo
import com.example.myclosetapp.data.SignUpResult
import com.example.myclosetapp.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    val binding by lazy {ActivitySignUpBinding.inflate(layoutInflater)}

    val retro = RetrofitService.create()

    // 회원정보 데이터 선언
    lateinit var signUpData: SignUpInfo
    var id: String? = null
    var pw: String? = null
    var pwChk: String? = null
    var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.signUpSignUp.setOnClickListener() {
            signUpFunc()
        }
    }

    fun signUpFunc() {
        // 사용자 입력값과 바인딩
        id = binding.signUpID.text.toString()
        pw = binding.signUpPW.text.toString()
        pwChk = binding.signUpPWCHK.text.toString()
        name = binding.signUpName.text.toString()

        // 값이 없다면 null 처리
        if(id!!.isEmpty()) id = null
        if(pw!!.isEmpty()) pw = null
        if(pwChk!!.isEmpty()) pwChk = null
        if(name!!.isEmpty()) name = null

        signUpData = SignUpInfo(id,name,pw,pwChk)

        // 회원가입 통신
        retro.postSignUpInfo(signUpData).enqueue(object: Callback<SignUpResult> {
            override fun onResponse(call: Call<SignUpResult>, response: Response<SignUpResult>) {
                if(response.body()?.isSuccess == true) {

                    // 로그
                    Log.d("MYTAG","회원가입 : "+response.body()?.isSuccess.toString())

                    // 메세지 출력
                    Toast.makeText(this@SignUpActivity, response.body()?.message, Toast.LENGTH_LONG)
                        .show()

                    // LoginActivity로 이동
                    val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {

                    // 로그
                    Log.d("MYTAG","회원가입 실패 : "+response.body()?.message)

                    // 메세지 출력
                    Toast.makeText(this@SignUpActivity, response.body()?.message, Toast.LENGTH_LONG)
                        .show()
                }
                clearUI()
            }

            override fun onFailure(call: Call<SignUpResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })
    }

    // 화면 초기화
    fun clearUI() {
        binding.signUpID.text = null
        binding.signUpPW.text = null
        binding.signUpPWCHK.text = null
        binding.signUpName.text = null

        binding.signUpID.clearFocus()
        binding.signUpPW.clearFocus()
        binding.signUpPWCHK.clearFocus()
        binding.signUpName.clearFocus()
    }
}