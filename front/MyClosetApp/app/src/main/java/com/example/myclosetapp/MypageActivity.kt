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

    var userIdx: Int? = null

    val retro = RetrofitService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        retro.getUserInfo(userIdx).enqueue(object: Callback<UserInfoResult> {
            override fun onResponse(call: Call<UserInfoResult>, response: Response<UserInfoResult>) {
                binding.textID.text = response.body()?.result?.id
                binding.textName.text = response.body()?.result?.name
                binding.textNumOfCloth.text = response.body()?.result?.numOfClth.toString()
            }

            override fun onFailure(call: Call<UserInfoResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })

        // 로그아웃 버튼 터치 시
        // ? 자동 로그인이 아닌 그냥 로그인 시에는 ??? 일단 정상실행은 됨
        binding.buttonLogout.setOnClickListener {
            // 기존 SharedPreferences 내 정보 삭제
//            AppData.editor.remove("id")
//            AppData.editor.remove("pw")
            AppData.editor.clear()
            AppData.editor.apply()

            Log.d("MYTAG", AppData.prefs.getString("id", null).toString())
            Log.d("MYTAG", AppData.prefs.getString("pw", null).toString())

            // 현재 모든 액티비티 종료 후 로그인 화면 이동
            finishAffinity()
            startActivity(Intent(this@MypageActivity, LoginActivity::class.java))
        }

        binding.buttonUpdate.setOnClickListener {
//            getAllCloth()
//            Toast.makeText(this,"옷장 최신화 중!!",Toast.LENGTH_SHORT).show()
            // 홈 화면으로 이동
            // 아니면 새로운 홈 화면 생성?
            val intent = Intent(this, ClosetActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
        }

        binding.buttonMypage.setOnClickListener() {
//            val intent = Intent(this, MypageActivity::class.java)
//            intent.putExtra("userIdx", userIdx)
//            startActivity(intent)
            Toast.makeText(this,"이미 마이페이지입니다!!!",Toast.LENGTH_SHORT).show()
        }

        binding.buttonBookmark.setOnClickListener() {
            val intent = Intent(this, BookmarkActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }
    }
}