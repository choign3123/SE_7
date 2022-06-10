package com.example.myclosetapp

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.example.myclosetapp.data.ClothInfo
import com.example.myclosetapp.data.PostClothResult
import com.example.myclosetapp.databinding.ActivityModifyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ModifyActivity : AppCompatActivity() {

    val binding by lazy { ActivityModifyBinding.inflate(layoutInflater) }

    val retro = RetrofitService.create()

    var userIdx: Int? = null
    var cloth = ClothInfo(null,null,null,null)

    // ??? 이거 m2랑 다른 게 뭐야
    private var isHearting:Boolean=false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        Log.d("MYTAG","모디파이 진입!!!")


//        userIdx = intent.getIntExtra("userIdx", 0)

        var m1: Int = 2                  // clthIdx
        var m2: Boolean = false            // favorite
        var mctgr: String? = "22"      // category
        var mss: String? = "2"           // season


        // 인텐트로 imgURL를 받은 경우
//        if (intent.hasExtra("imgURL")) {
//            var imgUrl = intent.getStringExtra("imgURL")
//            // 이미지 출력
//            binding.clothImage2.setImageURI(imgUrl?.toUri())
//
//            // clothInfo 변수 초기화
//            cloth.clthImgUrl = imgUrl
//            binding.saveInfo.setOnClickListener() {
//                cloth.bookmark = m2
//                cloth.category = mctgr
//                cloth.season = mss
//
//                postCloth()
//            }

//            var clothInfo = intent.getParcelableExtra<ClothInfo>("clothInfoKey")
//            var mdfclthIdx: Int = clothInfo!!.clthIdx
//            var mdfbookmark:Boolean = clothInfo.bookmark
//            var mdfcategory: String? = clothInfo.category
//            var mdfseason: String? = clothInfo.season
//            m1=mdfclthIdx;
//            m2=mdfbookmark;
//            mctgr=mdfcategory;
//            mss=mdfseason;
//            isHearting=m2;


//        var data = listOf(
//            "상의",//1
//            "하의",//2
//            "아우터",//3
//            "원피스/세트",//4
//            "기타",//5
//            "티셔츠",//6
//            "니트",//7
//            "셔츠",//8
//            "후드",//9
//            "맨투맨",//10
//            "스커트",//11
//            "팬츠",//12
//            "코트",//13
//            "패딩",//14
//            "집업",//15
//            "가디건",//16
//            "자켓")//17

        /* --- 버튼 클릭 시 변수에 저장 --------------------------------------*/
        // 밑과 동일
        binding.btnSpring.setOnClickListener { mss = "봄" }
        binding.btnSummer.setOnClickListener { mss = "여름" }
        binding.btnFall.setOnClickListener { mss = "가을" }
        binding.btnWinter.setOnClickListener { mss = "겨울" }
        // 이거 좀.. 이쁘게 만들자..
        binding.cate1.setOnClickListener { mctgr = "상의" }
        binding.cate2.setOnClickListener { mctgr = "하의" }
        binding.cate3.setOnClickListener { mctgr = "아우터" }
        binding.cate4.setOnClickListener { mctgr = "원피스/세트" }
        binding.cate5.setOnClickListener { mctgr = "기타" }
        binding.cate6.setOnClickListener { mctgr = "티셔츠" }
        binding.cate7.setOnClickListener { mctgr = "니트" }
        binding.cate8.setOnClickListener { mctgr = "셔츠" }
        binding.cate9.setOnClickListener { mctgr = "후드" }
        binding.cate10.setOnClickListener { mctgr = "맨투맨" }
        binding.cate11.setOnClickListener { mctgr = "스커트" }
        binding.cate12.setOnClickListener { mctgr = "팬츠" }
        binding.cate13.setOnClickListener { mctgr = "코트" }
        binding.cate14.setOnClickListener { mctgr = "패딩" }
        binding.cate15.setOnClickListener { mctgr = "집업" }
        binding.cate16.setOnClickListener { mctgr = "가디건" }
        binding.cate17.setOnClickListener { mctgr = "자켓" }
        /*---------------------------------------------------------*/


        //use textview ?
        // 옷 정보 수정하는 코드
        //m1=???
        //m2=???
        //mctgr=???
        //mss=???

//        var ModifiedClothInfo = MdfClothInfo(m1,isHearting,mctgr,mss)
//        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}

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

//    fun buttonSave() {
//        // 저장 버튼 클릭 시
//        binding.saveInfo.setOnClickListener {
//            //server에 m1,m2,mctgr,mss 저장한다.
//
//
////            val backToClothActivityIntent = Intent(this,MainActivity::class.java)
////            backToClothActivityIntent.putExtra("ModifiedClothInfo",ModifiedClothInfo)
////            setResult(AppCompatActivity.RESULT_OK,backToClothActivityIntent)
////            backToClothActivityIntent.putExtra("mdfclthIdx",m1)
////            backToClothActivityIntent.putExtra("mdfbookmark",isHearting)
////            backToClothActivityIntent.putExtra("mctgr",mctgr)
////            backToClothActivityIntent.putExtra("mss",mss)
////            activityResult.launch(backToClothActivityIntent)
//            // 위처럼 하지말고 startActivity 사용해보자
////            finish() // 굳이?
//        }

    // 즐겨찾기 버튼 클릭 시
    // !!! 첨 시작 시 값이 true면 채워져 있게끔 해야됨
    fun onClickButton(view: View) {
        if (!isHearting) {
            val animator = ValueAnimator.ofFloat(0F,0.5F).setDuration(500)
            animator.addUpdateListener {binding.likeBtn.progress = it.animatedValue as Float}
            animator.start()
            isHearting=true
            //m2=isHearting
        } else {
            val animator = ValueAnimator.ofFloat(0.5F,1F).setDuration(500)
            animator.addUpdateListener {binding.likeBtn.progress = it.animatedValue as Float}
            animator.start()
            isHearting=false
            //m2=isHearting
        }
    }
}



// 이거 뭐야
//class MdfClothInfo constructor (var mdfclthIdx:Int, var mdfbookmark:Boolean, var mdfcategory:String?,var mdfseason:String?) :
//    Parcelable {
//
//    constructor(parcel: Parcel) : this(
//        parcel.readInt(),
//        parcel.readBoolean(),
//        parcel.readString(),
//        parcel.readString()) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeInt(mdfclthIdx)
//        parcel.writeBoolean(mdfbookmark)
//        parcel.writeString(mdfcategory)
//        parcel.writeString(mdfseason)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<MdfClothInfo> {
//        override fun createFromParcel(parcel: Parcel): MdfClothInfo {
//            return MdfClothInfo(parcel)
//        }
//
//        override fun newArray(size: Int): Array<MdfClothInfo?> {
//            return arrayOfNulls(size)
//        }
//    }
//}