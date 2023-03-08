package com.egorponomarev.store.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.egorponomarev.store.R
import com.egorponomarev.store.data.dto.FlashSaleItem
import com.egorponomarev.store.databinding.FlashSaleListItemBinding
import com.egorponomarev.theme.base.PriceLabel
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

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
        mBinding.flashPrice.text = PriceLabel.Base(
            WeakReference(mBinding.root.context),
            price
        ).label()
        mBinding.discountLabel.text = mBinding.root.context.getString(
            R.string.discount_label,
            discount
        )
        mBinding.latestImage.clipToOutline = true
        Picasso.get().load(imageUrl).into(mBinding.latestImage)
        mBinding.root.setOnClickListener {
            mOnClick.invoke()
        }
    }
}