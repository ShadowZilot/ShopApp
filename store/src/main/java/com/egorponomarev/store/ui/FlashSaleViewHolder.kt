package com.egorponomarev.store.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.egorponomarev.store.data.dto.FlashSaleItem
import com.egorponomarev.store.databinding.FlashSaleListItemBinding
import com.squareup.picasso.Picasso

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class FlashSaleViewHolder(
    private val mBinding: FlashSaleListItemBinding,
    private val mOnClick: () -> Unit
) : ViewHolder(mBinding.root), FlashSaleItem.Mapper<Unit> {

    override fun map(
        category: String,
        name: String,
        price: Float,
        discount: Int,
        imageUrl: String
    ) {
        mBinding.flashCategory.text = category
        mBinding.flashName.text = name
        mBinding.flashPrice.text = price.toString()
        mBinding.discountLabel.text = "$discount % off"
        mBinding.latestImage.clipToOutline = true
        Picasso.get().load(imageUrl).into(mBinding.latestImage)
        mBinding.root.setOnClickListener {
            mOnClick.invoke()
        }
    }
}