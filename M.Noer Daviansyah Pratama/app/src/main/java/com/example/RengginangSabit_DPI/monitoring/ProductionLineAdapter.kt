package com.example.RengginangSabit_DPI.monitoring

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RengginangSabit_DPI.databinding.ItemProductionLineBinding

class ProductionLineAdapter(
    private var lineList: List<ProductionLine>,
    private val onItemClick: (ProductionLine) -> Unit
) : RecyclerView.Adapter<ProductionLineAdapter.LineViewHolder>() {

    class LineViewHolder(val binding: ItemProductionLineBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineViewHolder {
        val binding = ItemProductionLineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LineViewHolder, position: Int) {
        val item = lineList[position]
        holder.binding.apply {
            tvLineName.text = item.lineName
            tvLineStatusDesc.text = item.statusDesc
            tvLineOutput.text = item.outputRate
            tvLineMetrics.text = "Suhu: ${item.temperature} | Kelembaban: ${item.humidity}"

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = lineList.size

    fun updateData(newList: List<ProductionLine>) {
        lineList = newList
        notifyDataSetChanged()
    }
}
