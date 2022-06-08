package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import android.widget.RadioGroup
import android.widget.ToggleButton
import androidx.core.net.toUri
import com.example.myclosetapp.data.ClothInfo
import com.example.myclosetapp.data.ModifyInfo
import com.example.myclosetapp.data.ModifyResult
import com.example.myclosetapp.data.PostClothResult
import com.example.myclosetapp.databinding.ActivityModifyBinding
import com.example.myclosetapp.databinding.ActivityTestModifyBinding
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestModifyActivity : AppCompatActivity() {

    val binding by lazy { ActivityTestModifyBinding.inflate(layoutInflater) }

    val retro = RetrofitService.create()

    var userIdx: Int? = null
    var clothIdx: Int? = null
    var cloth = ClothInfo(null,null,null,null)
    var modCloth = ModifyInfo(null,null,null)

    // 북마크 null 받음 서버에서 false 처리해줌?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        Log.d("MYTAG","모디파이 진입!!!")

        userIdx = intent.getIntExtra("userIdx", 0)

        var myFav: Boolean? = null
        var myCategory: String? = null
        var mySeason: String? = null

        // 카메라 또는 갤러리에서 진입 시
        if (intent.hasExtra("imgURL")) {
            var imgUrl = intent.getStringExtra("imgURL")
            Log.d("MYTAG",imgUrl!!)
            // 이미지 출력
            binding.clothImage.setImageURI(imgUrl?.toUri())

            // clothInfo 변수 초기화
            cloth.clthImgUrl = imgUrl
            // 저장 버튼 클릭 시
            binding.buttonSave.setOnClickListener() {
                cloth.bookmark = myFav
                cloth.category = myCategory
                cloth.season = mySeason

                postCloth()

                // 화면 전환 // ??? 옷장 화면으로? 개별 옷 화면으로?
                val intent = Intent(this, ClosetActivity::class.java)
                startActivity(intent)
            }
        }

        // 옷 정보 화면에서 진입 시
        if(intent.hasExtra("clothURL")) {
            var clothURL = intent.getStringExtra("clothURL")
            binding.clothImage.setImageURI(clothURL?.toUri())

            // 해당 액티비티 내 로컬 변수 초기화
            clothIdx = intent.getIntExtra("clothIdx", 0)
            cloth.clthImgUrl = clothURL
            myFav = intent.getBooleanExtra("bookmark", false)
            myCategory = intent.getStringExtra("category")
            mySeason = intent.getStringExtra("season")

            binding.buttonSave.setOnClickListener() {
                modCloth.bookmark = myFav
                modCloth.category = myCategory
                modCloth.season = mySeason

                modifyCloth()

                // 화면 전환 // 여기서 인텐트로 값 2개 주거나..
                val intent = Intent(this, ClothActivity::class.java)
                intent.putExtra("userIdx", userIdx)
                intent.putExtra("clothIdx", clothIdx)
                startActivity(intent)
                finish()
            }
        }

        // 버튼 클릭 시 해당 값 변수에 저장
        binding.buttonSpring.setOnClickListener() {mySeason = binding.buttonSpring.text.toString()}
        binding.buttonSummer.setOnClickListener() {mySeason = binding.buttonSummer.text.toString()}
        binding.buttonFall.setOnClickListener() {mySeason = binding.buttonFall.text.toString()}
        binding.buttonWinter.setOnClickListener() {mySeason = binding.buttonWinter.text.toString()}

        binding.button9.setOnClickListener() {myCategory = binding.button9.text.toString()}
        binding.button10.setOnClickListener() {myCategory = binding.button10.text.toString()}
        binding.button11.setOnClickListener() {myCategory = binding.button11.text.toString()}
        binding.button12.setOnClickListener() {myCategory = binding.button12.text.toString()}

        // 즐겨찾기
        val toggle: ToggleButton = binding.toggleButtonFAV

        if(myFav == true) toggle.isChecked = true
        else toggle.isChecked = false

        toggle.setOnCheckedChangeListener() { _, isChecked ->
            if(isChecked) {
                myFav = true
            }
            else {
                myFav = false
            }

            Log.d("MYTAG",myFav.toString())
        }
//        binding.toggleButtonFAV.setOnCheckedChangeListener(MyToggleListener())
//
//        Log.d("MYTAG", "기존" + m.toString())
//        binding.toggleButtonFAV.setOnClickListener() {
//            binding.toggleButtonFAV.toggle()
//            myFav = binding.toggleButtonFAV.isChecked
//            Log.d("MYTAG", "터치 이후" + myFav.toString())
//        }

    }
    fun postCloth() {
        // 옷 등록
        retro.postCloth(userIdx, cloth).enqueue(object: Callback<PostClothResult> {
            override fun onResponse(call: Call<PostClothResult>, response: Response<PostClothResult>) {
                Log.d("MYTAG", response.body()?.isSuccess.toString())
                Log.d("MYTAG", response.body()?.message!!)
                Log.d("MYTAG", response.body()?.result.toString())

            }

            override fun onFailure(call: Call<PostClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    fun modifyCloth() {
        // 옷 수정
        retro.modifyCloth(userIdx, clothIdx, modCloth).enqueue(object: Callback<ModifyResult>{
            override fun onResponse(call: Call<ModifyResult>, response: Response<ModifyResult>) {
                Log.d("MYTAG", response.body()?.isSuccess.toString())
                Log.d("MYTAG", response.body()?.message!!)
                Log.d("MYTAG", response.body()?.result.toString())
            }

            override fun onFailure(call: Call<ModifyResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }

        })
    }



}