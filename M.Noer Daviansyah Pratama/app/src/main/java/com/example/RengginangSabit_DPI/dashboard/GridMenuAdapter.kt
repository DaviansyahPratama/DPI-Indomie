package com.example.RengginangSabit_DPI.dashboard

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RengginangSabit_DPI.databinding.ItemGridMenuBinding

class GridMenuAdapter(
    private val menuList: List<GridMenuItem>,
    private val onItemClick: (GridMenuItem) -> Unit
) : RecyclerView.Adapter<GridMenuAdapter.GridViewHolder>() {

    inner class GridViewHolder(val binding: ItemGridMenuBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        val binding = ItemGridMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        val item = menuList[position]
        holder.binding.apply {
            tvMenuTitle.text = item.title
            tvMenuSubtitle.text = item.subtitle
            imgMenuIcon.setImageResource(item.iconResId)
            val colorInt = Color.parseColor(item.accentColorHex)
            imgMenuIcon.backgroundTintList = ColorStateList.valueOf(colorInt)

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = menuList.size
}
