package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myclosetapp.data.AllClothResult
import com.example.myclosetapp.databinding.ActivitySearchResultBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchResultActivity : AppCompatActivity() {

    val binding by lazy {ActivitySearchResultBinding.inflate(layoutInflater)}
    val retro = RetrofitService.create()

    var userIdx : Int? = null
    var season : String? = null
    var category : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        season = intent.getStringExtra("season")
        category = intent.getStringExtra("category")


        // 검색결과 조회
        searchClothResult()

        // 하단 바 관련 메소드 (홈화면/검색/즐겨찾기/마이페이지)
        bottomBar()
    }

    fun searchClothResult() {
        retro.searchCloth(userIdx, season, category).enqueue(object : Callback<AllClothResult> {
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {

                ////// 그리드 뷰로 화면 출력 //////
                val listManager = GridLayoutManager(this@SearchResultActivity, 2)
                var listAdapter =
                    ListAdapterGrid(this@SearchResultActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridViewSearch.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }
                ////// 그리드 뷰로 화면 출력 //////

                // 로그
                Log.d("MYTAG", "검색 결과 조회 : "+response.body()?.isSuccess.toString())
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG", t.message.toString())
                Log.d("MYTAG", "FAIL")
            }

        })
    }

    fun bottomBar() {
        // 홈 버튼
        binding.buttonHome.setOnClickListener {
            val intent = Intent(this@SearchResultActivity, ClosetActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 마이페이지 버튼
        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this@SearchResultActivity, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 북마크 버튼
        binding.buttonBookmark.setOnClickListener() {
            val intent = Intent(this@SearchResultActivity, BookmarkActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        // 검색 버튼
        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this@SearchResultActivity, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }
    }
}