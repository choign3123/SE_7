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
import com.example.myclosetapp.data.AllClothObject
import com.example.myclosetapp.data.BookmarkObject
import com.example.myclosetapp.data.BookmarkResult
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
        retro.getBookmark(userIdx).enqueue(object: Callback<BookmarkResult>{
            override fun onResponse(call: Call<BookmarkResult>, response: Response<BookmarkResult>) {
                binding.gridViewFav.adapter =
                    GridViewAdapter(this@BookmarkActivity, response.body()!!.result)
            }

            override fun onFailure(call: Call<BookmarkResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")            }

        })
    }

    inner class GridViewAdapter(val context: Context, val img_list: ArrayList<BookmarkObject>): BaseAdapter() {


        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val grid_View: View = LayoutInflater.from(context).inflate(R.layout.item_grid, null)

            // 여기 뷰바인딩으로 해결 시도!
            // binding_item.gridViewImg.setImageURI(img_list[p0]!!.clthImgUrl.toUri())
            val img = grid_View.findViewById<ImageView>(R.id.gridViewImg)
            img.setImageURI(img_list[p0].clthImgUrl.toUri())

            // 그리드뷰에서 개별 옷 클릭 시
            img.setOnClickListener() {
                val intent = Intent( context ,ClothActivity::class.java)
                intent.putExtra("userIdx", userIdx)
                intent.putExtra("clothIdx", img_list[p0]!!.clthIdx)

                startActivity(intent)
            }




            return grid_View
        }

        override fun getCount(): Int {
            return img_list.size
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }
    }
}