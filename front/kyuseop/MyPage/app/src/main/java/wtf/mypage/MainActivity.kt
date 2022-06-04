package wtf.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import wtf.mypage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var userIdx:Int=1
        var isSuccess:Boolean=true
        var code:Int=1000
        var message:String="요청에 성공했습니다."
        // result : object ?
        var id:String="1"
        var name:String="임규섭"
        var numOfClth:Int=2

        binding.identiINFO.text=id;
        binding.usernameINFO.text=name;
        binding.numofclthinfo.text="${numOfClth}";

    }
}