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

import com.example.myclosetapp.databinding.ActivityBookmarkBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookmarkActivity : AppCompatActivity() {

    val binding by lazy {ActivityBookmarkBinding.inflate(layoutInflater)}

    var userIdx: Int? = null

    val retro = RetrofitService.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        retro.getBookmark(userIdx).enqueue(object: Callback<AllClothResult>{
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {
                val listManager = GridLayoutManager(this@BookmarkActivity,2)
                var listAdapter = ListAdapterGrid(this@BookmarkActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridViewFav.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")            }

        })

        binding.buttonUpdate.setOnClickListener {
//            getAllCloth()
//            Toast.makeText(this,"옷장 최신화 중!!",Toast.LENGTH_SHORT).show()
            // 홈 화면으로 이동
            // 아니면 새로운 홈 화면 생성?

            // 여기 그냥 홈 화면 생성이 아닌 이동하게끔!!!
            val intent = Intent(this, ClosetActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

        binding.buttonBookmark.setOnClickListener() {
//            val intent = Intent(this, BookmarkActivity::class.java)
//            intent.putExtra("userIdx", userIdx)
//            startActivity(intent)
            Toast.makeText(this,"이미 즐겨찾기 화면입니다!!!", Toast.LENGTH_SHORT).show()

        }

        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
            finish()
        }

    }
}