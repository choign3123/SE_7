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
    var id: String? = AppData.prefs.getString("id", null)
    var pw: String? = AppData.prefs.getString("pw", null)
    var loginData: LoginInfo = LoginInfo(id,pw)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        Log.d("MYTAG", "자동 로그인 ID : "+AppData.prefs.getString("id", null).toString())
        Log.d("MYTAG", "자동 로그인 PW : "+AppData.prefs.getString("pw", null).toString())

        // 자동 로그인 관련 메소드
        autoLoginFun()

        // 비밀번호 입력 중 키보드에서 완료 버튼을 누른 경우
        binding.loginPW.setOnEditorActionListener(MyEnterListener())
        // 비밀번호 입력 후 로그인 버튼 클릭 시
        binding.loginLogin.setOnClickListener() {
            bindingFun()
            loginFun(loginData)
        }

        // 회원가입 버튼 클릭 시
        binding.loginSignUp.setOnClickListener() {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
            clearUI()
        }
    }



    // 키보드에서 완료 버튼을 누른 경우
    inner class MyEnterListener : TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
            // keyboard 변수의 리턴 값에 따라 키보드가 숨겨지거나 계속 있거나 함
            var keyboard = false

            // 비밀번호 입력 중 키보드에서 완료 버튼을 눌렀을 경우
            if (p1 == EditorInfo.IME_ACTION_DONE)   {
                bindingFun()
                loginFun(loginData)
            }
            return keyboard
        }
    }

    // 바인딩 메소드
    fun bindingFun() {
        // 아이디 바인딩
        id = binding.loginID.text.toString()
        if(id!!.isEmpty()) id = null
        // 비밀번호 바인딩
        pw = binding.loginPW.text.toString()
        if(pw!!.isEmpty()) pw = null

        // 로그인 정보 초기화
        loginData = LoginInfo(id,pw)
    }

    fun autoLoginFun() {
        if(id != null && pw != null) { loginFun(loginData) }
    }


    fun loginFun(loginData: LoginInfo) {
        // 자동 로그인을 선택했다면 해당 정보 SharedPreferences에 저장
        if(binding.switchAutoLogin.isChecked) {
            AppData.editor.putString("id", id)
            AppData.editor.putString("pw", pw)
            AppData.editor.apply()
        }

        // 로그인 통신
        retro.postLoginInfo(loginData).enqueue(object: Callback<LoginResult> {
            override fun onResponse(call: Call<LoginResult>, response: Response<LoginResult>) {

                Log.d("MYTAG", "로그인 통신 "+response.body()?.isSuccess.toString())

                // 성공 여부에 따른 간단한 메세지 출력 및 옷장 화면 진입
                if(response.body()?.isSuccess == true) {
                    Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@LoginActivity, ClosetActivity::class.java)
                    intent.putExtra("userIdx", response.body()?.result?.userIdx)
                    startActivity(intent)
                    finish()
                }
                else {
                    Toast.makeText(this@LoginActivity, response.body()?.message, Toast.LENGTH_SHORT)
                        .show()

                    // 화면 초기화
                    clearUI()
                }

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