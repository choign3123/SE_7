package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import android.util.Log
import com.example.myclosetapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {

    val binding by lazy { ActivitySearchBinding.inflate(layoutInflater)}

    var userIdx: Int? = null

    var myFav: Boolean? = null
    var myCategory: String? = " "
    var mySeason: String? = " "

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)

        binding.buttonSpring.setOnClickListener(){clearSeason();binding.buttonSpring.isEnabled=false}
        binding.buttonSummer.setOnClickListener(){clearSeason();binding.buttonSummer.isEnabled=false}
        binding.buttonFall.setOnClickListener(){clearSeason();binding.buttonFall.isEnabled=false}
        binding.buttonWinter.setOnClickListener(){clearSeason();binding.buttonWinter.isEnabled=false}

        binding.button9.setOnClickListener(){clearCategory();binding.button9.isEnabled=false}
        binding.button10.setOnClickListener(){clearCategory();binding.button10.isEnabled=false}
        binding.button11.setOnClickListener(){clearCategory();binding.button11.isEnabled=false}
        binding.button12.setOnClickListener(){clearCategory();binding.button12.isEnabled=false}
        binding.button13.setOnClickListener(){clearCategory();binding.button13.isEnabled=false}
        binding.button14.setOnClickListener(){clearCategory();binding.button14.isEnabled=false}
        binding.button15.setOnClickListener(){clearCategory();binding.button15.isEnabled=false}
        binding.button16.setOnClickListener(){clearCategory();binding.button16.isEnabled=false}
        binding.button17.setOnClickListener(){clearCategory();binding.button17.isEnabled=false}
        binding.button18.setOnClickListener(){clearCategory();binding.button18.isEnabled=false}
        binding.button19.setOnClickListener(){clearCategory();binding.button19.isEnabled=false}
        binding.button20.setOnClickListener(){clearCategory();binding.button20.isEnabled=false}
        binding.button21.setOnClickListener(){clearCategory();binding.button21.isEnabled=false}
        binding.button22.setOnClickListener(){clearCategory();binding.button22.isEnabled=false}
        binding.button23.setOnClickListener(){clearCategory();binding.button23.isEnabled=false}
        binding.button24.setOnClickListener(){clearCategory();binding.button24.isEnabled=false}

        binding.switchBM.setOnCheckedChangeListener { _, isChecked ->  }

        binding.buttonGo.setOnClickListener() {
            // 초기화 // 검색결과에서 다시 검색 시
            myFav= null
            myCategory = " "
            mySeason = " "

            // season
            if(binding.buttonSpring.isChecked == true) mySeason = mySeason +" "+binding.buttonSpring.textOn
            if(binding.buttonSummer.isChecked == true) mySeason = mySeason +" "+binding.buttonSummer.textOn
            if(binding.buttonFall.isChecked == true) mySeason = mySeason +" "+binding.buttonFall.textOn
            if(binding.buttonWinter.isChecked == true) mySeason = mySeason +" "+binding.buttonWinter.textOn

            // category
            if(binding.button9.isChecked == true) myCategory = myCategory +" "+binding.button9.textOn
            if(binding.button10.isChecked == true) myCategory = myCategory +" "+binding.button10.textOn
            if(binding.button11.isChecked == true) myCategory = myCategory +" "+binding.button11.textOn
            if(binding.button12.isChecked == true) myCategory = myCategory +" "+binding.button12.textOn
            if(binding.button13.isChecked == true) myCategory = myCategory +" "+binding.button13.textOn
            if(binding.button14.isChecked == true) myCategory = myCategory +" "+binding.button14.textOn
            if(binding.button15.isChecked == true) myCategory = myCategory +" "+binding.button15.textOn
            if(binding.button16.isChecked == true) myCategory = myCategory +" "+binding.button16.textOn
            if(binding.button17.isChecked == true) myCategory = myCategory +" "+binding.button17.textOn
            if(binding.button18.isChecked == true) myCategory = myCategory +" "+binding.button18.textOn
            if(binding.button19.isChecked == true) myCategory = myCategory +" "+binding.button19.textOn
            if(binding.button20.isChecked == true) myCategory = myCategory +" "+binding.button20.textOn
            if(binding.button21.isChecked == true) myCategory = myCategory +" "+binding.button21.textOn
            if(binding.button22.isChecked == true) myCategory = myCategory +" "+binding.button22.textOn
            if(binding.button23.isChecked == true) myCategory = myCategory +" "+binding.button23.textOn
            if(binding.button24.isChecked == true) myCategory = myCategory +" "+binding.button24.textOn

            // favorite
            if(binding.switchBM.isChecked == true) myFav = true
            else myFav = false

            // 선택이 없다면 공백 보내기 선택이 있다면 공백 지우기
            if(mySeason != " ") mySeason = mySeason!!.trim()
            if(myCategory != " ") myCategory = myCategory!!.trim()

            val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("season", mySeason)
            intent.putExtra("category", myCategory)
            intent.putExtra("bookmark", myFav)

            startActivity(intent)
        }

    }

    fun clearSeason() {
        binding.buttonSpring.isEnabled=true
        binding.buttonSummer.isEnabled=true
        binding.buttonFall.isEnabled=true
        binding.buttonWinter.isEnabled=true
    }

    fun clearCategory() {
        binding.button9.isEnabled=true
        binding.button10.isEnabled=true
        binding.button11.isEnabled=true
        binding.button12.isEnabled=true
        binding.button13.isEnabled=true
        binding.button14.isEnabled=true
        binding.button15.isEnabled=true
        binding.button16.isEnabled=true
        binding.button17.isEnabled=true
        binding.button18.isEnabled=true
        binding.button19.isEnabled=true
        binding.button20.isEnabled=true
        binding.button21.isEnabled=true
        binding.button22.isEnabled=true
        binding.button23.isEnabled=true
        binding.button24.isEnabled=true

    }

}