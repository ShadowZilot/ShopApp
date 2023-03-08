package com.egorponomarev.good_detail.ui

import android.widget.ImageView
import android.widget.LinearLayout
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

        override fun setupImages(images: List<String>) {
            Picasso.get().load(
                images.first()
            ).into(mSelectedImage)
        }
    }
}