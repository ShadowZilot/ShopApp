package com.egorponomarev.store.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.egorponomarev.store.data.dto.FlashSaleItem
import com.egorponomarev.store.databinding.FlashSaleListItemBinding

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class FlashSaleAdapter(
    private val mOnClick: () -> Unit
) : Adapter<FlashSaleViewHolder>() {
    private val mData = mutableListOf<FlashSaleItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FlashSaleViewHolder(
        FlashSaleListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        mOnClick
    )

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: FlashSaleViewHolder, position: Int) {
        mData[position].map(holder)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun fetchData(data: List<FlashSaleItem>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }
}