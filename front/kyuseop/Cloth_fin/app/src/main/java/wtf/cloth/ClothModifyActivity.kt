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
        var clthInfo2 = ClothInformation(1,120,"null","null")

        binding.radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            //1st param = widget , 2nd param = checked widget's id.
            when (checkedId) {
                //미선택 시 존치!!!!!!!!!
                R.id.radioSpring -> clthInfo2.season="spring";//=mss;
                R.id.radioSummer -> clthInfo2.season="summer";
                R.id.radioAutumn -> clthInfo2.season="autumn";
                R.id.radioWinter -> clthInfo2.season="winter";
            }
        }
        mss=clthInfo2.season
        var mctgr : String? = "22"
        binding.radioGroup3.setOnCheckedChangeListener { group2, checkedId2 ->
            when (checkedId2) {
                //미선택 시 존치!!!!!!!!!
                R.id.radioShirt -> clthInfo2.category = "shirt";
                R.id.radioPants -> clthInfo2.category = "pants";
                R.id.radioHat -> clthInfo2.category = "hat";
                R.id.radioBoots -> clthInfo2.category = "boots";
            }
        }
        mctgr = clthInfo2.category
        var m1:Int = 2
        var m2:Int = 120

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
            clthInfo2.clthIdx=mdfclthIdx
            clthInfo2.bookmark=mdfbookmark
            // bookmark > 0 : db.bookmark = true; else false;
            clthInfo2.category=mctgr
            clthInfo2.season=mss
        }
        //use textview ?
        // 옷 정보 수정하는 코드
        //m1=???
        //m2=???
        //mctgr=???
        //mss=???
        binding.favOnOff.setOnClickListener{
            if (m2 >0) {m2 *= -1}
            else if (m2 <0) {m2 *= -1}
        }
// ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^그냥 즐찾 등록 , 해제 버튼 따로 만들어.
        var ModifiedClothInfo=MdfClothInfo(clthInfo2.clthIdx,clthInfo2.bookmark,clthInfo2.category,clthInfo2.season)
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){}

        binding.saveInfo.setOnClickListener {
            val backToClothActivityIntent = Intent(this,MainActivity::class.java)
            backToClothActivityIntent.putExtra("ModifiedClothInfo",ModifiedClothInfo)
            setResult(RESULT_OK,backToClothActivityIntent)
            backToClothActivityIntent.putExtra("mdfclthIdx",clthInfo2.clthIdx)
            // bookmark > 0 : db.bookmark = true; else false;
            backToClothActivityIntent.putExtra("mdfbookmark",clthInfo2.bookmark)
            backToClothActivityIntent.putExtra("mctgr",clthInfo2.category)
            backToClothActivityIntent.putExtra("mss",clthInfo2.season)
            activityResult.launch(backToClothActivityIntent)
            finish()
        }
    }
}

class MdfClothInfo constructor (var mdfclthIdx:Int,var mdfbookmark:Int,var mdfcategory:String?,var mdfseason:String?):Parcelable{

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