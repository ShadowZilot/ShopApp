package com.egorponomarev.good_detail.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import com.squareup.picasso.Picasso

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface ImageCarousel {

    fun setupImages(images: List<String>)

    class Base(
        private val mSelectedImage: ImageView,
        private val mContainer: LinearLayout
    ) : ImageCarousel {
        private val mItems = mutableListOf<CarouselItem>()
        private var mCurrent = 0

        override fun setupImages(images: List<String>) {
            mSelectedImage.clipToOutline = true
            Picasso.get().load(images.first()).into(mSelectedImage)
            for (i in images.indices) {
                mItems.add(
                    CarouselItem.Base(
                        mContainer.context,
                        i,
                        {
                            onImageSelect(it)
                        },
                        images[i]
                    )
                )
                mContainer.addView(mItems[i].view())
            }
        }

        private fun onImageSelect(index: Int) {
            if (index != mCurrent) {
                val animSet = AnimatorSet()
                animSet.playTogether(
                    ObjectAnimator.ofFloat(
                        mItems[mCurrent].view(), "elevation", 12f, 0f
                    ),
                    ObjectAnimator.ofFloat(
                        mItems[mCurrent].view(), "scaleX", 1.3f, 1f
                    ),
                    ObjectAnimator.ofFloat(
                        mItems[mCurrent].view(), "scaleY", 1.1f, 1f
                    ),
                    ObjectAnimator.ofFloat(
                        mItems[index].view(), "elevation", 0f, 12f
                    ),
                    ObjectAnimator.ofFloat(
                        mItems[index].view(), "scaleX", 1f, 1.3f
                    ),
                    ObjectAnimator.ofFloat(
                        mItems[index].view(), "scaleY", 1f, 1.1f
                    )
                )
                mCurrent = index
                animSet.duration = 250
                animSet.doOnEnd {
                    Picasso.get().load(mItems[index].url()).into(mSelectedImage)
                }
                animSet.start()
            }
        }
    }
}