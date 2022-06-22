package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myclosetapp.data.UserInfoResult
import com.example.myclosetapp.databinding.ActivityMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageActivity : AppCompatActivity() {

    val binding by lazy {ActivityMypageBinding.inflate(layoutInflater)}
    val retro = RetrofitService.create()

    var userIdx: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)

        // 유저 정보 조회
        getUserInfo()

        // 로그아웃 버튼 터치 시
        logOut()

        // 하단 바 관련 메소드 (홈화면/검색/즐겨찾기/마이페이지)
        bottomBar()
    }

    fun getUserInfo() {
        retro.getUserInfo(userIdx).enqueue(object: Callback<UserInfoResult> {
            override fun onResponse(call: Call<UserInfoResult>, response: Response<UserInfoResult>) {

                // 로그
                Log.d("MYTAG","사용자 정보 조회 : "+response.body()?.isSuccess)

                // 화면에 바인딩
                binding.textID.text = response.body()?.result?.id
                binding.textName.text = response.body()?.result?.name
                binding.textNumOfCloth.text = response.body()?.result?.numOfClth.toString()
            }

            override fun onFailure(call: Call<UserInfoResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })
    }

    fun logOut() {
        binding.buttonLogout.setOnClickListener {
            // 기존 SharedPreferences 내 정보 삭제
            AppData.editor.clear()
            AppData.editor.apply()

            // 로그
            Log.d("MYTAG", "자동 로그인 ID 해제 -> "+AppData.prefs.getString("id", null).toString())
            Log.d("MYTAG", "자동 로그인 PW 해제 -> "+AppData.prefs.getString("pw", null).toString())

            // 메세지 출력
            Toast.makeText(this@MypageActivity,"로그아웃 완료!!!",Toast.LENGTH_SHORT).show()

            // 현재 모든 액티비티 종료 후 로그인 화면 이동
            finishAffinity()
            startActivity(Intent(this@MypageActivity, LoginActivity::class.java))
        }
    }

    fun bottomBar() {
        // 홈 버튼
        binding.buttonHome.setOnClickListener {
            val intent = Intent(this@MypageActivity, ClosetActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 마이페이지 버튼
        binding.buttonMypage.setOnClickListener() {
            Toast.makeText(this@MypageActivity,"이미 마이페이지입니다!!!",Toast.LENGTH_LONG).show()
        }

        // 즐겨찾기 버튼
        binding.buttonBookmark.setOnClickListener() {
            val intent = Intent(this@MypageActivity, BookmarkActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 검색 버튼
        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this@MypageActivity, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }
    }
}