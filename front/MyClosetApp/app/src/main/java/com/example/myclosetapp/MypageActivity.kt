package com.example.myclosetapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    }
}