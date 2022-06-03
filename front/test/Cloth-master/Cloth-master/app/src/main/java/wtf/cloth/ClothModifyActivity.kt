package wtf.cloth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.content.Intent
import android.os.Parcelable
import wtf.cloth.databinding.ActivityClothModifyBinding

class ClothModifyActivity : AppCompatActivity() {
    val binding by lazy { ActivityClothModifyBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.clothImage2.setImageResource(intent.getIntExtra("img", 0))

        var mss:String? = "2"
        binding.radioGroup2.setOnCheckedChangeListener { group, checkedId ->
            //1st param = widget , 2nd param = checked widget's id.
            when (checkedId) {
                R.id.radioSpring -> mss="spring";
                R.id.radioSummer -> mss="summer";
                R.id.radioAutumn -> mss="autumn";
                R.id.radioWinter -> mss="winter";
            }
        }

        var mctgr : String? = "22"
        binding.radioGroup3.setOnCheckedChangeListener { group2, checkedId2 ->
            when (checkedId2) {
                R.id.radioShirt -> mctgr = "shirt";
                R.id.radioPants -> mctgr = "pants";
                R.id.radioHat -> mctgr = "hat";
                R.id.radioBoots -> mctgr = "boots";
            }
        }

        var m1:Int = 2
        var m2:Boolean = false

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
        }
        //use textview ?
        // 옷 정보 수정하는 코드
        //m1=???
        //m2=???
        //mctgr=???
        //mss=???
        binding.favOnOff.setOnClickListener {
            m2 = !(m2); //즐겨찾기 반대로 바꾸기만 하는 버튼
        }

        var ModifiedClothInfo = MdfClothInfo(m1,m2,mctgr,mss)

        binding.saveInfo.setOnClickListener {
            val backToClothActivityIntent = Intent(this,MainActivity::class.java)
            backToClothActivityIntent.putExtra("ModifiedClothInfo",ModifiedClothInfo)
            setResult(RESULT_OK,backToClothActivityIntent)
            startActivity(backToClothActivityIntent)
            finish()
        }
    }
}

class MdfClothInfo constructor (var mdfclthIdx: Int, var mdfbookmark:Boolean, var mdfcategory: String?,var mdfseason:String?) :
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