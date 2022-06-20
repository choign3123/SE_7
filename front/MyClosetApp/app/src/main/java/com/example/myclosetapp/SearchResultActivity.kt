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
    var bm : Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        season = intent.getStringExtra("season")
        category = intent.getStringExtra("category")
        bm = intent.getBooleanExtra("bookmark", false)

        Log.d("MYTAG","진입 완료")
        Log.d("MYTAG",season!!)
        Log.d("MYTAG",category!!)
        Log.d("MYTAG",bm.toString())


        retro.searchCloth(userIdx, season, category, bm).enqueue(object : Callback<AllClothResult> {
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {
                val listManager = GridLayoutManager(this@SearchResultActivity, 2)
                var listAdapter =
                    ListAdapterGrid(this@SearchResultActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridViewSearch.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })
    }
}