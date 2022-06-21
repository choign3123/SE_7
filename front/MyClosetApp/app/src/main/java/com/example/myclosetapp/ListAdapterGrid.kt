package com.example.myclosetapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myclosetapp.data.AllClothObject
import com.example.myclosetapp.databinding.ItemGridBinding


class ListAdapterGrid(val context: Context, var userIdx: Int?, val img_list: ArrayList<AllClothObject>)
    : RecyclerView.Adapter<ListAdapterGrid.GridAdapter>() {

    class GridAdapter(val binding: ItemGridBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)

        return GridAdapter(ItemGridBinding.bind(view))
    }

    override fun onBindViewHolder(holder: GridAdapter, position: Int) {
        val img = holder.binding.gridViewImg
        img.setImageURI(img_list[position].clthImgUrl.toUri())

        // 그리드 뷰에서 개별 옷 클릭 시
        img.setOnClickListener() {
            val intent = Intent( context ,ClothActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            intent.putExtra("clothIdx", img_list[position]!!.clthIdx)

            startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return img_list.size
    }
}