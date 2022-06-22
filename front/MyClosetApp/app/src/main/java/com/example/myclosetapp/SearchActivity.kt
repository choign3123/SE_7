package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myclosetapp.databinding.ActivitySearchBinding
//import android.util.Log

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater)}

    var userIdx: Int? = null

//    var myFav: Boolean? = null
    var myCategory: String? = " "
    var mySeason: String? = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)

        var springSel : Boolean = false
        var summerSel : Boolean = false
        var fallSel : Boolean = false
        var winterSel : Boolean = false
        binding.buttonSpring.setOnClickListener(){springSel= !springSel}
        binding.buttonSummer.setOnClickListener(){summerSel= !summerSel}
        binding.buttonFall.setOnClickListener(){fallSel= !fallSel}
        binding.buttonWinter.setOnClickListener(){winterSel= !winterSel}

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
        binding.button9.setOnClickListener(){btn9Sel= !btn9Sel}
        binding.button10.setOnClickListener(){btn10Sel= !btn10Sel}
        binding.button11.setOnClickListener(){btn11Sel= !btn11Sel}
        binding.button12.setOnClickListener(){btn12Sel= !btn12Sel}
        binding.button13.setOnClickListener(){btn13Sel= !btn13Sel}
        binding.button14.setOnClickListener(){btn14Sel= !btn14Sel}
        binding.button15.setOnClickListener(){btn15Sel= !btn15Sel}
        binding.button16.setOnClickListener(){btn16Sel= !btn16Sel}
        binding.button17.setOnClickListener(){btn17Sel= !btn17Sel}
        binding.button18.setOnClickListener(){btn18Sel= !btn18Sel}
        binding.button19.setOnClickListener(){btn19Sel= !btn19Sel}
        binding.button20.setOnClickListener(){btn20Sel= !btn20Sel}
        binding.button21.setOnClickListener(){btn21Sel= !btn21Sel}
        binding.button22.setOnClickListener(){btn22Sel= !btn22Sel}
        binding.button23.setOnClickListener(){btn23Sel= !btn23Sel}
        binding.button24.setOnClickListener(){btn24Sel= !btn24Sel}

//        binding.switchBM.setOnCheckedChangeListener{_,isChecked -> }

        binding.buttonGo.setOnClickListener(){
            // 초기화 // 검색결과에서 다시 검색 시
//            myFav = null
            myCategory = " "
            mySeason = " "

            // season
            if(springSel) mySeason = mySeason + " " + binding.buttonSpring.text.substring(1)
            if(summerSel) mySeason = mySeason + " " + binding.buttonSummer.text.substring(1)
            if(fallSel) mySeason = mySeason + " " + binding.buttonFall.text.substring(1)
            if(winterSel) mySeason = mySeason + " " + binding.buttonWinter.text.substring(1)

            // category
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

            // favorite
//            if(binding.switchBM.isChecked == true) myFav = true
//            else myFav = false

            // 선택이 없다면 공백 보내기 선택이 있다면 공백 지우기
            if(mySeason != " ") mySeason = mySeason!!.trim()
            if(myCategory != " ") myCategory = myCategory!!.trim()

            val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("season", mySeason)
            intent.putExtra("category", myCategory)
//            intent.putExtra("bookmark", myFav)

            startActivity(intent)
        }
    }
}