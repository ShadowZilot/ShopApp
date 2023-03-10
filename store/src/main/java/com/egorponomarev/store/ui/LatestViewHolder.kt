package com.egorponomarev.store.ui

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.egorponomarev.store.data.dto.LatestItem
import com.egorponomarev.store.databinding.LatestListItemBinding
import com.egorponomarev.theme.base.PriceLabel
import com.squareup.picasso.Picasso
import java.lang.ref.WeakReference

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class LatestViewHolder(
    private val mBinding: LatestListItemBinding,
    private val mOnCLicked: () -> Unit
) : ViewHolder(mBinding.root), LatestItem.Mapper<Unit> {

    override fun map(category: String, name: String,
                     price: Float, imageUrl: String) {
        mBinding.latestCategory.text = category
        mBinding.latestName.text = name
        mBinding.latestImage.clipToOutline = true
        Picasso.get().load(
            imageUrl
        ).into(mBinding.latestImage)
        mBinding.latestPrice.text = PriceLabel.Base(
            WeakReference(mBinding.root.context),
            price
        ).label()
        mBinding.root.setOnClickListener {
            mOnCLicked.invoke()
        }
    }
}