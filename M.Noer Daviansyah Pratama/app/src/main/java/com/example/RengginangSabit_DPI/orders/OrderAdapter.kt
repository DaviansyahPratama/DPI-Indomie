package com.example.RengginangSabit_DPI.orders

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.RengginangSabit_DPI.databinding.ItemOrderBinding

class OrderAdapter(
    private var orderList: List<Order>,
    private val onItemClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.binding.apply {
            tvOrderId.text = order.id
            tvCustomerName.text = order.customerName
            tvOrderDate.text = "Tanggal: ${order.orderDate}"
            tvOrderQuantity.text = "Jumlah: ${order.quantityKg} kg"
            tvOrderTotal.text = order.totalPrice
            tvOrderStatus.text = order.status

            // Background status badge
            val badgeColor = when (order.status) {
                "Baru" -> Color.parseColor("#2196F3")
                "Diproses" -> Color.parseColor("#E89B2C")
                "Selesai" -> Color.parseColor("#27AE60")
                else -> Color.parseColor("#757575")
            }
            tvOrderStatus.setBackgroundColor(badgeColor)

            root.setOnClickListener {
                onItemClick(order)
            }
        }
    }

    override fun getItemCount(): Int = orderList.size

    fun updateData(newList: List<Order>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
