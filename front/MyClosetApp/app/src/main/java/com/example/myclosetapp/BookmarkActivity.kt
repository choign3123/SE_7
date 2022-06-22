package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myclosetapp.data.AllClothResult
import com.example.myclosetapp.databinding.ActivityBookmarkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkActivity : AppCompatActivity() {

    val binding by lazy {ActivityBookmarkBinding.inflate(layoutInflater)}
    val retro = RetrofitService.create()

    var userIdx: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)

        // 즐겨찾기된 옷 조회
        getBookmark()
        // 하단 바 관련 메소드 (홈화면/검색/즐겨찾기/마이페이지)
        bottomBar()

    }

    fun getBookmark() {
        retro.getBookmark(userIdx).enqueue(object: Callback<AllClothResult>{
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {

                // 로그
                Log.d("MYTAG","즐겨찾기 조회 : "+response.body()?.isSuccess)

                ////// 그리드 뷰로 화면 출력 //////
                val listManager = GridLayoutManager(this@BookmarkActivity,2)
                var listAdapter = ListAdapterGrid(this@BookmarkActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridViewFav.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }
                ////// 그리드 뷰로 화면 출력 //////
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    fun bottomBar() {
        // 홈 버튼
        binding.buttonHome.setOnClickListener {
            val intent = Intent(this@BookmarkActivity, ClosetActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 마이페이지 버튼
        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this@BookmarkActivity, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 북마크 버튼 // 같은 화면이므로 전환하지 않는다.
        binding.buttonBookmark.setOnClickListener() {
            Toast.makeText(this@BookmarkActivity,"이미 즐겨찾기 화면입니다!!!", Toast.LENGTH_SHORT).show()
        }

        // 검색 버튼
        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this@BookmarkActivity, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }
    }
}