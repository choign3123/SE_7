package wtf.cloth

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.content.Intent
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import wtf.cloth.databinding.ActivityClothModifyBinding
import java.util.*

class ClothModifyActivity : AppCompatActivity() {
    val binding by lazy { ActivityClothModifyBinding.inflate(layoutInflater) }
    private var isHearting:Boolean=false;
    val TAG:String="로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var mss:String? = "2"

        binding.clothImage2.setImageResource(intent.getIntExtra("img", 0))

        var mctgr : String? = "22"

        var m1:Int = 2
        var m2:Boolean=false

        var data = listOf(
            "상의",//1
            "하의",//2
            "아우터",//3
            "원피스/세트",//4
            "기타",//5
            "티셔츠",//6
            "니트",//7
            "셔츠",//8
            "후드",//9
            "맨투맨",//10
            "스커트",//11
            "팬츠",//12
            "코트",//13
            "패딩",//14
            "집업",//15
            "가디건",//16
            "자켓")//17
        binding.btnSpring.setOnClickListener { mss="봄" }
        binding.btnSummer.setOnClickListener { mss="여름" }
        binding.btnFall.setOnClickListener { mss="가을" }
        binding.btnWinter.setOnClickListener { mss="겨울" }

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
        binding.cate17.setOnClickListener { mctgr = "자켓" };//

        if (intent.hasExtra("clothInfoKey")) {
            var clothInfo = intent.getParcelableExtra<ClothInfo>("clothInfoKey")
            var mdfclthIdx: Int = clothInfo!!.clthIdx
            var mdfbookmark:Boolean = clothInfo.bookmark
            var mdfcategory: String? = clothInfo.category
            var mdfseason: String? = clothInfo.season
            m1=mdfclthIdx;
            m2=mdfbookmark;
            mctgr=mdfcategory;
            mss=mdfseason;
            isHearting=m2;
        }
        //use textview ?
        // 옷 정보 수정하는 코드
        //m1=???
        //m2=???
        //mctgr=???
        //mss=???

        var ModifiedClothInfo = MdfClothInfo(m1,isHearting,mctgr,mss)
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}

        binding.saveInfo.setOnClickListener {
            //server에 m1,m2,mctgr,mss 저장한다.
            val backToClothActivityIntent = Intent(this,MainActivity::class.java)
            backToClothActivityIntent.putExtra("ModifiedClothInfo",ModifiedClothInfo)
            setResult(RESULT_OK,backToClothActivityIntent)
            backToClothActivityIntent.putExtra("mdfclthIdx",m1)
            backToClothActivityIntent.putExtra("mdfbookmark",isHearting)
            backToClothActivityIntent.putExtra("mctgr",mctgr)
            backToClothActivityIntent.putExtra("mss",mss)
            activityResult.launch(backToClothActivityIntent)
            finish()
        }
    }

    fun onClickButton(view: View) {
        if (!isHearting) {
            val animator = ValueAnimator.ofFloat(0F,0.5F).setDuration(500)
            animator.addUpdateListener {binding.likeBtn.progress = it.animatedValue as Float}
            animator.start()
            isHearting=true
            //m2=isHearting
            Log.d(TAG,"좋아요 버튼 클릭됨.")
        } else {
            val animator = ValueAnimator.ofFloat(0.5F,1F).setDuration(500)
            animator.addUpdateListener {binding.likeBtn.progress = it.animatedValue as Float}
            animator.start()
            isHearting=false
            //m2=isHearting
            Log.d(TAG,"좋아요 버튼 OFF")
        }
    }

}

class MdfClothInfo constructor (var mdfclthIdx:Int, var mdfbookmark:Boolean, var mdfcategory:String?,var mdfseason:String?) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readBoolean(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mdfclthIdx)
        parcel.writeBoolean(mdfbookmark)
        parcel.writeString(mdfcategory)
        parcel.writeString(mdfseason)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MdfClothInfo> {
        override fun createFromParcel(parcel: Parcel): MdfClothInfo {
            return MdfClothInfo(parcel)
        }

        override fun newArray(size: Int): Array<MdfClothInfo?> {
            return arrayOfNulls(size)
        }
    }
}