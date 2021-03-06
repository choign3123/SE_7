package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myclosetapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater)}

    var userIdx: Int? = null

    var myCategory: String? = " "
    var mySeason: String? = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)


        // season 선택 값 초기화
        var springSel : Boolean = false
        var summerSel : Boolean = false
        var fallSel : Boolean = false
        var winterSel : Boolean = false
        // season 버튼 클릭 시
        binding.buttonSpring.setOnClickListener{springSel=true
            binding.buttonSpring.isEnabled=false}
        binding.buttonSummer.setOnClickListener{summerSel=true
            binding.buttonSummer.isEnabled=false}
        binding.buttonFall.setOnClickListener{fallSel=true
            binding.buttonFall.isEnabled=false}
        binding.buttonWinter.setOnClickListener{winterSel=true
            binding.buttonWinter.isEnabled=false}

        // category 선택 값 초기화
        var btn9Sel : Boolean = false
        var btn10Sel : Boolean = false
        var btn11Sel : Boolean = false
        var btn12Sel : Boolean = false
        var btn13Sel : Boolean = false
        var btn14Sel : Boolean = false
        var btn15Sel : Boolean = false
        var btn16Sel : Boolean = false
        var btn17Sel : Boolean = false
        var btn18Sel : Boolean = false
        var btn19Sel : Boolean = false
        var btn20Sel : Boolean = false
        var btn21Sel : Boolean = false
        var btn22Sel : Boolean = false
        var btn23Sel : Boolean = false
        var btn24Sel : Boolean = false
        // category 버튼 클릭 시
        binding.button9.setOnClickListener{btn9Sel=true
            binding.button9.isEnabled=false}
        binding.button10.setOnClickListener{btn10Sel=true
            binding.button10.isEnabled=false}
        binding.button11.setOnClickListener{btn11Sel=true
            binding.button11.isEnabled=false}
        binding.button12.setOnClickListener{btn12Sel=true
            binding.button12.isEnabled=false}
        binding.button13.setOnClickListener{btn13Sel=true
            binding.button13.isEnabled=false}
        binding.button14.setOnClickListener{btn14Sel=true
            binding.button14.isEnabled=false}
        binding.button15.setOnClickListener{btn15Sel=true
            binding.button15.isEnabled=false}
        binding.button16.setOnClickListener{btn16Sel=true
            binding.button16.isEnabled=false}
        binding.button17.setOnClickListener{btn17Sel=true
            binding.button17.isEnabled=false}
        binding.button18.setOnClickListener{btn18Sel=true
            binding.button18.isEnabled=false}
        binding.button19.setOnClickListener{btn19Sel=true
            binding.button19.isEnabled=false}
        binding.button20.setOnClickListener{btn20Sel=true
            binding.button20.isEnabled=false}
        binding.button21.setOnClickListener{btn21Sel=true
            binding.button21.isEnabled=false}
        binding.button22.setOnClickListener{btn22Sel=true
            binding.button22.isEnabled=false}
        binding.button23.setOnClickListener{btn23Sel=true
            binding.button23.isEnabled=false}
        binding.button24.setOnClickListener{btn24Sel=true
            binding.button24.isEnabled=false}

        // 검색 버튼 터치 시
        binding.buttonGo.setOnClickListener(){
            // 초기화 // 검색결과에서 다시 검색 시
            myCategory = " "
            mySeason = " "


            // season 바인딩 // 다중 검색을 위해 기존 season에 공백 추가 후 바인딩 // 바인딩 시 #제외 후 바인딩
            if(springSel) mySeason = mySeason + " " + binding.buttonSpring.text.substring(1)
            if(summerSel) mySeason = mySeason + " " + binding.buttonSummer.text.substring(1)
            if(fallSel) mySeason = mySeason + " " + binding.buttonFall.text.substring(1)
            if(winterSel) mySeason = mySeason + " " + binding.buttonWinter.text.substring(1)

            // category // 다중 검색을 위해 기존 category에 공백 추가 후 바인딩 // 바인딩 시 #제외 후 바인딩
            if(btn9Sel) myCategory = myCategory + " " + binding.button9.text.substring(1)
            if(btn10Sel) myCategory = myCategory + " " + binding.button10.text.substring(1)
            if(btn11Sel) myCategory = myCategory + " " + binding.button11.text.substring(1)
            if(btn12Sel) myCategory = myCategory + " " + binding.button12.text.substring(1)
            if(btn13Sel) myCategory = myCategory + " " + binding.button13.text.substring(1)
            if(btn14Sel) myCategory = myCategory + " " + binding.button14.text.substring(1)
            if(btn15Sel) myCategory = myCategory + " " + binding.button15.text.substring(1)
            if(btn16Sel) myCategory = myCategory + " " + binding.button16.text.substring(1)
            if(btn17Sel) myCategory = myCategory + " " + binding.button17.text.substring(1)
            if(btn18Sel) myCategory = myCategory + " " + binding.button18.text.substring(1)
            if(btn19Sel) myCategory = myCategory + " " + binding.button19.text.substring(1)
            if(btn20Sel) myCategory = myCategory + " " + binding.button20.text.substring(1)
            if(btn21Sel) myCategory = myCategory + " " + binding.button21.text.substring(1)
            if(btn22Sel) myCategory = myCategory + " " + binding.button22.text.substring(1)
            if(btn23Sel) myCategory = myCategory + " " + binding.button23.text.substring(1)
            if(btn24Sel) myCategory = myCategory + " " + binding.button24.text.substring(1)


            // 선택이 없다면 공백 보내기 // 선택이 있다면 공백 지우기
            if(mySeason != " ") mySeason = mySeason!!.trim()
            if(myCategory != " ") myCategory = myCategory!!.trim()

            // 검색결과화면으로 이동
            val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("season", mySeason)
            intent.putExtra("category", myCategory)

            startActivity(intent)
            finish()
        }
    }
}