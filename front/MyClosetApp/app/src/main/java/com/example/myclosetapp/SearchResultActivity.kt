package com.example.myclosetapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myclosetapp.data.AllClothObject
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
//    var bm : Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        season = intent.getStringExtra("season")
        category = intent.getStringExtra("category")
//        bm = intent.getBooleanExtra("bookmark", false)

        Log.d("MYTAG","진입 완료")
        Log.d("MYTAG",season!!)
        Log.d("MYTAG",category!!)
//        Log.d("MYTAG",bm.toString())


        retro.searchCloth(userIdx, season, category).enqueue(object : Callback<AllClothResult> {
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {
                val listManager = GridLayoutManager(this@SearchResultActivity, 2)
                var listAdapter =
                    ListAdapterGrid(this@SearchResultActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridViewSearch.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }

                Log.d("MYTAG",response.body()?.result.toString())
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })

        binding.buttonUpdate.setOnClickListener {
//            getAllCloth()
//            Toast.makeText(this,"옷장 최신화 중!!",Toast.LENGTH_SHORT).show()
            // 홈 화면으로 이동
            // 아니면 새로운 홈 화면 생성?
            val intent = Intent(this, ClosetActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivity(intent)
            finish()
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
//            finish()
            // 고려
//            Toast.makeText(this,"이미 검색 화면입니다!!!",Toast.LENGTH_SHORT).show()

        }
    }
}