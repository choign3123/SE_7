package wtf.cloth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import wtf.cloth.databinding.ActivityClothModifyBinding

class ClothModifyActivity : AppCompatActivity() {
    val binding by lazy { ActivityClothModifyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.clothImage2.setImageResource(intent.getIntExtra("img", 0))

        var mss:String? = "2"
        binding.radioGroup2.setOnCheckedChangeListener {_, checkedId ->
            //1st param = widget , 2nd param = checked widget's id.
            when (checkedId) {
                R.id.radioSpring -> mss="봄";
                R.id.radioSummer -> mss="여름";
                R.id.radioAutumn -> mss="가을";
                R.id.radioWinter -> mss="겨울";
            }
        }

        var mctgr : String? = "22"
        binding.radioGroup3.setOnCheckedChangeListener {_, checkedId2 ->
            when (checkedId2) {
                R.id.radioShirt -> mctgr = "상의";
                R.id.radioPants -> mctgr = "하의";
                R.id.radioHat -> mctgr = "아우터";
                R.id.radioBoots -> mctgr = "원피스/세트";
                R.id.radioOthers->mctgr="기타";
            }
        }

        var m1:Int = 2
        var m2:Int = 120
        binding.radioGroup.setOnCheckedChangeListener {_,checkedId3 ->
            when (checkedId3) {
                R.id.favOn -> m2 = 120;
                R.id.favOff-> m2 =-120;
            }
        }

        if (intent.hasExtra("clothInfoKey")) {
            var clothInfo = intent.getParcelableExtra<ClothInfo>("clothInfoKey")
            var mdfclthIdx: Int = clothInfo!!.clthIdx
            var mdfbookmark:Int = clothInfo.bookmark
            var mdfcategory: String? = clothInfo.category
            var mdfseason: String? = clothInfo.season
            m1=mdfclthIdx;
            m2=mdfbookmark;
            mctgr=mdfcategory;
            mss=mdfseason;
        }
        //use textview ?
        // 옷 정보 수정하는 코드
        //m1=???
        //m2=???
        //mctgr=???
        //mss=???

        var ModifiedClothInfo = MdfClothInfo(m1,m2,mctgr,mss)
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}

        binding.saveInfo.setOnClickListener {
            val backToClothActivityIntent = Intent(this,MainActivity::class.java)
            backToClothActivityIntent.putExtra("ModifiedClothInfo",ModifiedClothInfo)
            setResult(RESULT_OK,backToClothActivityIntent)
            backToClothActivityIntent.putExtra("mdfclthIdx",m1)
            backToClothActivityIntent.putExtra("mdfbookmark",m2)
            backToClothActivityIntent.putExtra("mctgr",mctgr)
            backToClothActivityIntent.putExtra("mss",mss)
            activityResult.launch(backToClothActivityIntent)
            finish()
        }
    }
}

class MdfClothInfo constructor (var mdfclthIdx:Int, var mdfbookmark:Int, var mdfcategory:String?,var mdfseason:String?) :
    Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mdfclthIdx)
        parcel.writeInt(mdfbookmark)
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