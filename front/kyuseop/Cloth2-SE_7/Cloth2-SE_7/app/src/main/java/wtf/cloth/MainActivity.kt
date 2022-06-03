package wtf.cloth

import androidx.activity.result.contract.ActivityResultContracts
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import wtf.cloth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.clothImage.setImageResource(R.drawable.aaa)
        //임의로 설정함. 실제로는 이 액티비티가 실행되는 순간 밑에 있는 변수들의 값이 알맞게 조정돼야 함.
        //데이터 클래스 사용?
        val clthInfo1 = ClothInformation(1,120,"셔츠","autumn")
        //이거 쓰지 말까.
        var clthIdx: Int = 1
        var bookmark:Int=120
        var category: String = "셔츠"
        var season:String = "autumn"
        // 이제 정보를 받아 각 변수 초기화
        //clthInfo1.clthIdx = ???
        //clthInfo1.bookmark = ???
        //clthInfo1.category = ???
        //clthInfo1.season = ???

        //if (clthInfo1.bookmark) {binding.favorite.text = "즐겨찾기 등록됨."}
        //else if(clthInfo1.bookmark){binding.favorite.text = "즐겨찾기 해제됨."}

        var myClothInfo = ClothInfo(clthInfo1.clthIdx, clthInfo1.bookmark, clthInfo1.category,clthInfo1.season);
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {

        }
        if (intent.hasExtra("ModifiedClothInfo")) {
            var MdfclothInfo = intent.getParcelableExtra<MdfClothInfo>("ModifiedClothInfo")
            var mdfclthIdx: Int = MdfclothInfo!!.mdfclthIdx
            var mdfbookmark:Int = MdfclothInfo.mdfbookmark
            var mdfcategory: String? = MdfclothInfo.mdfcategory
            var mdfseason: String? = MdfclothInfo.mdfseason
            // 이제 정보를 again 각 변수 초기화
            clthInfo1.clthIdx = intent.getIntExtra("mdfclthIdx",1)
            clthInfo1.bookmark = intent.getIntExtra("mdfbookmark",-120)
            clthInfo1.category = intent.getStringExtra("mctgr")
            clthInfo1.season = intent.getStringExtra("mss")
            //if (clthInfo1.bookmark) {binding.favorite.text = "즐겨찾기 등록됨."}
            //else if(clthInfo1.bookmark){binding.favorite.text = "즐겨찾기 해제됨."}
        }
        if (clthInfo1.bookmark>0) {binding.favorite.text = "즐겨찾기 등록됨."}
        else if(clthInfo1.bookmark<0){binding.favorite.text = "즐겨찾기 해제됨."}
        binding.modifybtn.setOnClickListener {
            val nextIntent = Intent(this, ClothModifyActivity::class.java)
            nextIntent.putExtra("clothInfoKey",myClothInfo)
            nextIntent.putExtra("img",R.drawable.aaa)
            activityResult.launch(nextIntent);
        }
        binding.showCate.text= "${clthInfo1.category}"
        binding.season.text="${clthInfo1.season}"
    }
}
//여기 밑에는 안 건듦?
class ClothInfo constructor(var clthIdx:Int, var bookmark:Int, var category:String?,var season:String?):Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clthIdx)
        parcel.writeInt(bookmark)
        parcel.writeString(category)
        parcel.writeString(season)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ClothInfo> {
        override fun createFromParcel(parcel: Parcel): ClothInfo {
            return ClothInfo(parcel)
        }

        override fun newArray(size: Int): Array<ClothInfo?> {
            return arrayOfNulls(size)
        }
    }
}