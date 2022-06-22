package com.example.myclosetapp

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.net.toUri
import com.example.myclosetapp.data.ClothInfo
import com.example.myclosetapp.data.ModifyInfo
import com.example.myclosetapp.data.ModifyResult
import com.example.myclosetapp.data.PostClothResult
import com.example.myclosetapp.databinding.ActivityModifyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyActivity : AppCompatActivity() {

    val binding by lazy { ActivityModifyBinding.inflate(layoutInflater) }

    val retro = RetrofitService.create()

    var userIdx: Int? = null
    var clothIdx: Int? = null
    var cloth = ClothInfo(null,null,null,null)
    var modCloth = ModifyInfo(null,null,null)

    var myFav: Boolean? = null
    var tempFav : Boolean? = false
    var myCategory: String? = null
    var mySeason: String? = null

    var heartClickCnt : ULong = 0u
    val uLong1 : ULong = 1u

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)


        // 버튼 클릭 시 해당 값 변수에 저장
        bindingButton()

        // 즐겨찾기 버튼 터치 시
        bookmarkFun0()

        /******************************************************************************************/

        // 카메라 또는 갤러리에서 진입 시
        if (intent.hasExtra("imgURL")) {

            val imgUrl = intent.getStringExtra("imgURL")
            // 이미지 출력
            binding.clothImage.setImageURI(imgUrl?.toUri())


            // clothInfo 변수 초기화
            cloth.clthImgUrl = imgUrl

            ////// 즐겨찾기 애니메이션 관련 //////
            bookmarkFun1()
            ////// 즐겨찾기 애니메이션 관련 //////

            // 로그
            Log.d("MYTAG","카메라/갤러리 진입 : "+imgUrl!!)

            // 저장 버튼 클릭 시
            binding.buttonSave.setOnClickListener() {
                // 화면과 바인딩
                if (heartClickCnt == uLong1) {myFav=false}
                cloth.bookmark = myFav
                cloth.category = myCategory
                cloth.season = mySeason

                // 옷 등록
                postCloth()

                // 옷 등록 이후 0.2초 이후 화면 전환 // 바로 전환 시 옷장 출력 이후 옷 등록이 되는 경우 발생
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@ModifyActivity, ClosetActivity::class.java)
                    intent.putExtra("userIdx", userIdx)

                    startActivity(intent)
                    finish()
                },200)
            }
        }

        /******************************************************************************************/

        // 옷 정보 화면에서 진입 시
        if(intent.hasExtra("clothURL")) {

            val clothURL = intent.getStringExtra("clothURL")
            // 이미지 출력
            binding.clothImage.setImageURI(clothURL?.toUri())

            clothIdx = intent.getIntExtra("clothIdx", 0)
            cloth.clthImgUrl = clothURL
            myFav = intent.getBooleanExtra("bookmark", false)
            myCategory = intent.getStringExtra("category")
            mySeason = intent.getStringExtra("season")


            ////// 즐겨찾기 애니메이션 관련 //////
            bookmarkFun2()
            ////// 즐겨찾기 애니메이션 관련 //////

            //선택 계절에 해당하는 버튼 눌러놓기
            whichSeason()
            // 선택 카테고리에 해당하는 버튼 눌러놓기
            whichCategory(myCategory)


            //저장 버튼 클릭 시
            binding.buttonSave.setOnClickListener {
                // 바인딩
                modCloth.bookmark = myFav
                myCategory=myCategory
                modCloth.category = myCategory
                mySeason=mySeason
                modCloth.season = mySeason

                // 옷 수정
                modifyCloth()

                // 옷 등록 이후 0.2초 이후 ClothActivity 전환 // 바로 전환 시 옷장 출력 이후 옷 등록이 되는 경우 발생
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@ModifyActivity, ClothActivity::class.java)
                    intent.putExtra("userIdx", userIdx)
                    intent.putExtra("clothIdx", clothIdx)

                    startActivity(intent)
                    finish()
                },200)
            }
        }
    }

    fun postCloth() {
        retro.postCloth(userIdx, cloth).enqueue(object: Callback<PostClothResult> {
            override fun onResponse(call: Call<PostClothResult>, response: Response<PostClothResult>) {

                // 로그
                Log.d("MYTAG", "옷 등록 : " + response.body()?.isSuccess.toString())

                if (response.body()?.isSuccess == true) {
                    // 메세지 출력
                    Toast.makeText(this@ModifyActivity, response.body()?.result, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@ModifyActivity, response.body()?.message, Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            }

            override fun onFailure(call: Call<PostClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    fun modifyCloth() {
        retro.modifyCloth(userIdx, clothIdx, modCloth).enqueue(object: Callback<ModifyResult>{
            override fun onResponse(call: Call<ModifyResult>, response: Response<ModifyResult>) {
                // 로그
                Log.d("MYTAG","옷 수정 : "+response.body()?.isSuccess.toString())

                // 메세지 출력
                Toast.makeText(this@ModifyActivity, response.body()?.result, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<ModifyResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    fun bookmarkFun0() {
        binding.likeBtn.setOnClickListener {
            if (myFav == false) {
                val animator=ValueAnimator.ofFloat(0f,0.5f).setDuration(500)
                animator.addUpdateListener {
                    binding.likeBtn.progress = it.animatedValue as Float
                }
                animator.start()
                myFav = true
            } else {
                val animator = ValueAnimator.ofFloat(0.5f,1f).setDuration(500)
                animator.addUpdateListener {
                    binding.likeBtn.progress = it.animatedValue as Float
                }
                animator.start()
                myFav = false
            }
        }
    }

    fun bookmarkFun1() {
        if (tempFav != true) {
            val animator=ValueAnimator.ofFloat(0f,0.5f).setDuration(1)
            animator.addUpdateListener {
                binding.likeBtn.progress = it.animatedValue as Float
            }
            animator.start()
            tempFav=true
            myFav=true
        }
        // 즐겨찾기 버튼 클릭 시
        binding.likeBtn.setOnClickListener {
            heartClickCnt += 1u
            if (tempFav == false) {
                val animator=ValueAnimator.ofFloat(0f,0.5f).setDuration(500)
                animator.addUpdateListener {
                    binding.likeBtn.progress = it.animatedValue as Float
                }
                animator.start()
                tempFav = true
                myFav = tempFav
            }
            else {
                val animator = ValueAnimator.ofFloat(0.5f,1f).setDuration(500)
                animator.addUpdateListener {
                    binding.likeBtn.progress = it.animatedValue as Float
                }
                animator.start()
                tempFav = false
                myFav = tempFav
            }
        }
    }

    fun bookmarkFun2() {
        if (myFav != true) {
            val animator=ValueAnimator.ofFloat(0.99f,1f).setDuration(10)
            animator.addUpdateListener {
                binding.likeBtn.progress = it.animatedValue as Float
            }
            animator.start()
        }
        else {
            val animator = ValueAnimator.ofFloat(0.49f,0.5f).setDuration(10)
            animator.addUpdateListener {
                binding.likeBtn.progress = it.animatedValue as Float
            }
            animator.start()
        }
    }

    fun clearSeason() {
        binding.buttonSpring.isEnabled=true
        binding.buttonSummer.isEnabled=true
        binding.buttonFall.isEnabled=true
        binding.buttonWinter.isEnabled=true
    }

    fun clearCategory() {
        binding.button9.isEnabled=true
        binding.button10.isEnabled=true
        binding.button11.isEnabled=true
        binding.button12.isEnabled=true
        binding.button13.isEnabled=true
        binding.button14.isEnabled=true
        binding.button15.isEnabled=true
        binding.button16.isEnabled=true
        binding.button17.isEnabled=true
        binding.button18.isEnabled=true
        binding.button19.isEnabled=true
        binding.button20.isEnabled=true
        binding.button21.isEnabled=true
        binding.button22.isEnabled=true
        binding.button23.isEnabled=true
        binding.button24.isEnabled=true
    }

    fun whichSeason() {
        if ("#"+mySeason == binding.buttonSpring.text) {
            clearSeason()
            binding.buttonSpring.isEnabled=false
        } else if ("#"+mySeason == binding.buttonSummer.text) {
            clearSeason()
            binding.buttonSummer.isEnabled=false
        } else if ("#"+mySeason == binding.buttonFall.text) {
            clearSeason()
            binding.buttonFall.isEnabled=false
        } else if ("#"+mySeason == binding.buttonWinter.text) {
            clearSeason()
            binding.buttonWinter.isEnabled=false
        }
    }

    fun whichCategory(myCate: String?) {
        if ("#"+myCate == binding.button9.text) {
            clearCategory()
            binding.button9.isEnabled=false
        }
        else if ("#"+myCate == binding.button10.text) {
            clearCategory()
            binding.button10.isEnabled=false
        }
        else if ("#"+myCate == binding.button11.text) {
            clearCategory()
            binding.button11.isEnabled=false
        }
        else if ("#"+myCate == binding.button12.text) {
            clearCategory()
            binding.button12.isEnabled=false
        }
        else if ("#"+myCate == binding.button13.text) {
            clearCategory()
            binding.button13.isEnabled=false
        }
        else if ("#"+myCate == binding.button14.text) {
            clearCategory()
            binding.button14.isEnabled=false
        }
        else if ("#"+myCate == binding.button15.text) {
            clearCategory()
            binding.button15.isEnabled=false
        }
        else if ("#"+myCate == binding.button16.text) {
            clearCategory()
            binding.button16.isEnabled=false
        }
        else if ("#"+myCate == binding.button17.text) {
            clearCategory()
            binding.button17.isEnabled=false
        }
        else if ("#"+myCate == binding.button18.text) {
            clearCategory()
            binding.button18.isEnabled=false
        }
        else if ("#"+myCate == binding.button19.text) {
            clearCategory()
            binding.button19.isEnabled=false
        }
        else if ("#"+myCate == binding.button20.text) {
            clearCategory()
            binding.button20.isEnabled=false
        }
        else if ("#"+myCate == binding.button21.text) {
            clearCategory()
            binding.button21.isEnabled=false
        }
        else if ("#"+myCate == binding.button22.text) {
            clearCategory()
            binding.button22.isEnabled=false
        }
        else if ("#"+myCate == binding.button23.text) {
            clearCategory()
            binding.button23.isEnabled=false
        }
        else if ("#"+myCate == binding.button24.text) {
            clearCategory()
            binding.button24.isEnabled=false
        }
    }

    fun bindingButton() {

        // 계절
        binding.buttonSpring.setOnClickListener{
            mySeason=binding.buttonSpring.text.toString().substring(1)
            clearSeason()
            binding.buttonSpring.isEnabled=false
        }
        binding.buttonSummer.setOnClickListener{
            mySeason=binding.buttonSummer.text.toString().substring(1)
            clearSeason()
            binding.buttonSummer.isEnabled=false
        }
        binding.buttonFall.setOnClickListener{
            mySeason=binding.buttonFall.text.toString().substring(1)
            clearSeason()
            binding.buttonFall.isEnabled=false
        }
        binding.buttonWinter.setOnClickListener{
            mySeason=binding.buttonWinter.text.toString().substring(1)
            clearSeason()
            binding.buttonWinter.isEnabled=false
        }
        // 카테고리
        binding.button9.setOnClickListener{
            myCategory=binding.button9.text.toString().substring(1)
            clearCategory()
            binding.button9.isEnabled=false
        }
        binding.button10.setOnClickListener{
            myCategory=binding.button10.text.toString().substring(1)
            clearCategory()
            binding.button10.isEnabled=false
        }
        binding.button11.setOnClickListener{
            myCategory=binding.button11.text.toString().substring(1)
            clearCategory()
            binding.button11.isEnabled=false
        }
        binding.button12.setOnClickListener{
            myCategory=binding.button12.text.toString().substring(1)
            clearCategory()
            binding.button12.isEnabled=false
        }
        binding.button13.setOnClickListener{
            myCategory=binding.button13.text.toString().substring(1)
            clearCategory()
            binding.button13.isEnabled=false
        }
        binding.button14.setOnClickListener{
            myCategory=binding.button14.text.toString().substring(1)
            clearCategory()
            binding.button14.isEnabled=false
        }
        binding.button15.setOnClickListener{
            myCategory=binding.button15.text.toString().substring(1)
            clearCategory()
            binding.button15.isEnabled=false
        }
        binding.button16.setOnClickListener{
            myCategory=binding.button16.text.toString().substring(1)
            clearCategory()
            binding.button16.isEnabled=false
        }
        binding.button17.setOnClickListener{
            myCategory=binding.button17.text.toString().substring(1)
            clearCategory()
            binding.button17.isEnabled=false
        }
        binding.button18.setOnClickListener{
            myCategory=binding.button18.text.toString().substring(1)
            clearCategory()
            binding.button18.isEnabled=false
        }
        binding.button19.setOnClickListener{
            myCategory=binding.button19.text.toString().substring(1)
            clearCategory()
            binding.button19.isEnabled=false
        }
        binding.button20.setOnClickListener{
            myCategory=binding.button20.text.toString().substring(1)
            clearCategory()
            binding.button20.isEnabled=false
        }
        binding.button21.setOnClickListener{
            myCategory=binding.button21.text.toString().substring(1)
            clearCategory()
            binding.button21.isEnabled=false
        }
        binding.button22.setOnClickListener{
            myCategory=binding.button22.text.toString().substring(1)
            clearCategory()
            binding.button22.isEnabled=false
        }
        binding.button23.setOnClickListener{
            myCategory=binding.button23.text.toString().substring(1)
            clearCategory()
            binding.button23.isEnabled=false
        }
        binding.button24.setOnClickListener{
            myCategory=binding.button24.text.toString().substring(1)
            clearCategory()
            binding.button24.isEnabled=false
        }
    }
}