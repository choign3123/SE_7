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
    }
}