package com.example.myclosetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        binding.buttonSpring.setOnCheckedChangeListener { _, isChecked ->  }
        binding.buttonSummer.setOnCheckedChangeListener { _, isChecked ->  }
        binding.buttonFall.setOnCheckedChangeListener { _, isChecked ->  }
        binding.buttonWinter.setOnCheckedChangeListener { _, isChecked ->  }

        binding.button9.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button10.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button11.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button12.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button13.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button14.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button15.setOnCheckedChangeListener { _, isChecked ->  }
        binding.button16.setOnClickListener(){binding.button16.isEnabled=false}
        binding.button17.setOnClickListener(){binding.button17.isEnabled=false}
        binding.button18.setOnClickListener(){binding.button18.isEnabled=false}
        binding.button19.setOnClickListener(){binding.button19.isEnabled=false}
        binding.button20.setOnClickListener(){binding.button20.isEnabled=false}
        binding.button21.setOnClickListener(){binding.button21.isEnabled=false}
        binding.button22.setOnClickListener(){binding.button22.isEnabled=false}
        binding.button23.setOnClickListener(){binding.button23.isEnabled=false}
        binding.button24.setOnClickListener(){binding.button24.isEnabled=false}
        binding.button25.setOnClickListener(){binding.button25.isEnabled=false}

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

            // favorite
            if(binding.switchBM.isChecked == true) myFav =true
            else myFav = false

            // 선택이 없다면 공백 보내기 선택이 있다면 공백 지우기
            if(mySeason != " ") mySeason = mySeason!!.trim()
            if(myCategory != " ") myCategory =myCategory!!.trim()

            val intent = Intent(this@SearchActivity, SearchResultActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("season", mySeason)
            intent.putExtra("category", myCategory)
            intent.putExtra("bookmark", myFav)

            startActivity(intent)
        }

    }



}