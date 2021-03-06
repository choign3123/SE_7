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

        // 개별 옷 정보 출력
        getCloth()
        // 삭제 버튼
        deleteCloth()

        // 수정 버튼
        binding.buttonMod.setOnClickListener() {
            var intent = Intent(this@ClothActivity, ModifyActivity::class.java)
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

    fun getCloth() {
        retro.getCloth(userIdx, clothIdx).enqueue(object: Callback<ClothResult>{
            override fun onResponse(call: Call<ClothResult>, response: Response<ClothResult>) {
                // 로컬 변수와 바인딩
                cloth.clthImgUrl = response.body()?.result?.clthImgUrl
                cloth.bookmark = response.body()?.result?.bookmark
                cloth.category = response.body()?.result?.category
                cloth.season = response.body()?.result?.season

                // 로그
                Log.d("MYTAG","개별 옷 : "+response.body()?.isSuccess)

                // 화면에 바인딩
                binding.imageCloth.setImageURI(cloth.clthImgUrl?.toUri())
                if (cloth.bookmark == true) {
                    binding.textBookmark.text = "즐겨찾기 등록됨"
                } else {
                    binding.textBookmark.text = "즐겨찾기 해제됨"
                }
                binding.textCategory.text = cloth.category
                binding.textSeason.text = cloth.season

            }

            override fun onFailure(call: Call<ClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    fun deleteCloth() {
        binding.buttonDel.setOnClickListener() {
            retro.deleteCloth(userIdx,clothIdx).enqueue(object: Callback<DeleteResult>{
                override fun onResponse(call: Call<DeleteResult>, response: Response<DeleteResult>) {
                    // 로그
                    Log.d("MYTAG","옷 삭제 : "+response.body()?.isSuccess)
                    // 옷 삭제 여부 메세지 출력
                    Toast.makeText(this@ClothActivity, response.body()?.result,Toast.LENGTH_SHORT)
                        .show()

                    // 삭제 후 옷장 화면으로 이동
                    var intent = Intent(this@ClothActivity,ClosetActivity::class.java)
                    intent.putExtra("userIdx", userIdx)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }

                override fun onFailure(call: Call<DeleteResult>, t: Throwable) {
                    Log.d("MYTAG",t.message.toString())
                    Log.d("MYTAG","FAIL")
                }
            })
        }
    }
}