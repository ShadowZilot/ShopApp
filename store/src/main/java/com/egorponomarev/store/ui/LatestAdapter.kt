package com.egorponomarev.store.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.egorponomarev.store.data.dto.LatestItem
import com.egorponomarev.store.databinding.LatestListItemBinding

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class LatestAdapter(
    private val mOnClick: () -> Unit
) : Adapter<LatestViewHolder>() {
    private val mData = mutableListOf<LatestItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LatestViewHolder(
        LatestListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        mOnClick
    )

    override fun getItemCount() = mData.size

    override fun onBindViewHolder(holder: LatestViewHolder, position: Int) {
        mData[position].map(holder)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun fetchData(data: List<LatestItem>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }
}