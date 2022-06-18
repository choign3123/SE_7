package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.example.myclosetapp.data.ClothInfo
import com.example.myclosetapp.data.ClothResult
import com.example.myclosetapp.data.DeleteResult
import com.example.myclosetapp.databinding.ActivityClothBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClothActivity : AppCompatActivity() {

    val binding by lazy { ActivityClothBinding.inflate(layoutInflater)}

    val retro = RetrofitService.create()

    var userIdx: Int? = null
    var clothIdx: Int? = null
    var cloth = ClothInfo(null,null,null,null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        clothIdx = intent.getIntExtra("clothIdx", 0)


        retro.getCloth(userIdx, clothIdx).enqueue(object: Callback<ClothResult>{
            override fun onResponse(call: Call<ClothResult>, response: Response<ClothResult>) {
                cloth.clthImgUrl = response.body()?.result?.clthImgUrl
                cloth.bookmark = response.body()?.result?.bookmark
                cloth.category = response.body()?.result?.category
                cloth.season = response.body()?.result?.season

                binding.imageCloth.setImageURI(cloth.clthImgUrl?.toUri())
                binding.textBookmark.text = cloth.bookmark.toString()
                binding.textCategory.text = cloth.category
                binding.textSeason.text = cloth.season
                if (cloth.bookmark == true) {
                    binding.textBookmark.text = "즐겨찾기 등록됨"
                } else {
                    binding.textBookmark.text = "즐겨찾기 해제됨"
                }
            }

            override fun onFailure(call: Call<ClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })

        Log.d("MYTAG","in create"+cloth.clthImgUrl.toString())



        // 삭제 버튼
        binding.buttonDel.setOnClickListener() {
            retro.deleteCloth(userIdx,clothIdx).enqueue(object: Callback<DeleteResult>{
                override fun onResponse(call: Call<DeleteResult>, response: Response<DeleteResult>) {
                    Log.d("MYTAG",response.body()?.message!!)
                    Toast.makeText(this@ClothActivity, response.body()?.message,Toast.LENGTH_SHORT)

                    // 삭제 후 옷장 화면으로 이동
                    var intent = Intent(this@ClothActivity,ClosetActivity::class.java)
                    startActivity(intent)
                }

                override fun onFailure(call: Call<DeleteResult>, t: Throwable) {
                    Log.d("MYTAG",t.message.toString())
                    Log.d("MYTAG","FAIL")
                }
            })
        }

        // 수정 버튼
        binding.buttonMod.setOnClickListener() {
            var intent = Intent(this@ClothActivity, TestModifyActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("clothIdx", clothIdx)
            intent.putExtra("clothURL", cloth.clthImgUrl)
            intent.putExtra("bookmark", cloth.bookmark)
            intent.putExtra("category", cloth.category)
            intent.putExtra("season", cloth.season)
            startActivity(intent)
            finish()
        }
    }
}