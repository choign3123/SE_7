package com.example.myclosetapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.example.myclosetapp.data.AllClothObject

class GridViewAdapter (val context: Context, var userIdx: Int?, val img_list: ArrayList<AllClothObject>): BaseAdapter() {

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val grid_View: View = LayoutInflater.from(context).inflate(R.layout.item_grid, null)

            // 여기 뷰바인딩으로 해결 시도!
            // binding_item.gridViewImg.setImageURI(img_list[p0]!!.clthImgUrl.toUri())
            val img = grid_View.findViewById<ImageView>(R.id.gridViewImg)
            img.setImageURI(img_list[p0].clthImgUrl.toUri())

            // 그리드뷰에서 개별 옷 클릭 시
            img.setOnClickListener() {
                val intent = Intent( context ,ClothActivity::class.java)
                intent.putExtra("userIdx", userIdx)
                intent.putExtra("clothIdx", img_list[p0]!!.clthIdx)

                startActivity(context, intent, null)
            }

            return grid_View
        }

    override fun getCount(): Int {
        return img_list.size
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }
}
