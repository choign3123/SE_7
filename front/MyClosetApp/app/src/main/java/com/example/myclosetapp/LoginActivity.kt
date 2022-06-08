package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.example.myclosetapp.databinding.ActivityLoginBinding
import com.example.myclosetapp.data.LoginInfo
import com.example.myclosetapp.data.LoginResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    val binding by lazy {ActivityLoginBinding.inflate(layoutInflater)}

    val retro = RetrofitService.create()

    // 로그인 데이터 선언
    lateinit var loginData: LoginInfo
    var id: String? = null
    var pw: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        // 비밀번호 입력 중 키보드에서 완료 버튼을 눌렀을 경우
        binding.loginPW.setOnEditorActionListener(MyEnterListener())
        // 로그인 버튼 클릭 시
        binding.loginLogin.setOnClickListener() {
            loginFunc()
        }
        // 회원가입 버튼 클릭 시
        binding.loginSignUp.setOnClickListener() {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            clearUI()
        }
    }

    inner class MyEnterListener : TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
            // 리턴 값에 따라 키보드가 숨겨지거나 계속 있거나 함
            var keyboard = false

            // 비밀번호 입력 중 키보드에서 완료 버튼을 눌렀을 경우
            if (p1 == EditorInfo.IME_ACTION_DONE)   loginFunc()
            return keyboard
        }
    }


    fun loginFunc() {
        // 아이디 바인딩
        id = binding.loginID.text.toString()
        // 비밀번호 바인딩
        pw = binding.loginPW.text.toString()

        // 입력이 없다면 null 처리
        if(id!!.isEmpty()) id = null
        if(pw!!.isEmpty()) pw = null

        // 로그인 정보 초기화
        loginData = LoginInfo(id,pw)

        retro.postLoginInfo(loginData).enqueue(object: Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {
                // 로그
                Log.d("MYTAG", response.body().toString())

                // 성공 여부에 따른 간단한 메세지 출력 및 옷장 화면 진입
                if(response.body()?.isSuccess == true) {
                    Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@LoginActivity, ClosetActivity::class.java)
                    intent.putExtra("userIdx", response.body()?.result?.userIdx)
                    startActivity(intent)
                    // 추가?
                    finish()
                }
                else {
                    Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT)
                        .show()
                }

                clearUI()
            }

            override fun onFailure(call: Call<LoginResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    // 화면 초기화
    fun clearUI() {
        binding.loginID.text = null
        binding.loginPW.text = null
        binding.loginID.clearFocus()
        binding.loginPW.clearFocus()
    }
}