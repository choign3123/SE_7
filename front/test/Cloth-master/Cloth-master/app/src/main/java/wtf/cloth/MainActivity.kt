package wtf.cloth

import androidx.activity.result.contract.ActivityResultContracts
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import wtf.cloth.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.clothImage.setImageResource(R.drawable.aaa)
        //임의로 설정함. 실제로는 이 액티비티가 실행되는 순간 밑에 있는 변수들의 값이 알맞게 조정돼야 함.
        //데이터 클래스 사용?
        var clthIdx: Int = 1
        var bookmark:Boolean = true
        var category: String? = "셔츠"
        var season:String? = "autumn"
        // 이제 정보를 받아 각 변수 초기화
        //clthIdx = ???
        //bookmark = ???
        //category = ???
        //season = ???

        if (bookmark) {
            binding.favorite.text = "즐겨찾기 등록됨."
        } else {
            binding.favorite.text = "즐겨찾기 해제됨."
        }

        var myClothInfo = ClothInfo(clthIdx, bookmark, category, season);
        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {

        }
        if (intent.hasExtra("ModifiedClothInfo")) {
            var MdfclothInfo = intent.getParcelableExtra<MdfClothInfo>("ModifiedClothInfo")
            var mdfclthIdx: Int = MdfclothInfo!!.mdfclthIdx
            var mdfbookmark:Boolean = MdfclothInfo.mdfbookmark
            var mdfcategory: String? = MdfclothInfo.mdfcategory
            var mdfseason: String? = MdfclothInfo.mdfseason
            // 이제 정보를 again 각 변수 초기화
            clthIdx = mdfclthIdx
            bookmark = mdfbookmark
            category = mdfcategory
            season = mdfseason
        }
        binding.modifybtn.setOnClickListener {
            val nextIntent = Intent(this, ClothModifyActivity::class.java)
            nextIntent.putExtra("clothInfoKey",myClothInfo)
            nextIntent.putExtra("img",R.drawable.aaa)
            activityResult.launch(nextIntent);
        }
        binding.showCate.text= "${category}"
        binding.season.text="${season}"
    }
}
//여기 밑에는 안 건듦?
class ClothInfo constructor (var clthIdx: Int, var bookmark:Boolean, var category: String?,var season:String?) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readBoolean(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(clthIdx)
        parcel.writeBoolean(bookmark)
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