package com.egorponomarev.good_detail.ui

import android.annotation.SuppressLint
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.egorponomarev.good_detail.R
import com.squareup.picasso.Picasso

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface CarouselItem {
    fun view() : View

    fun url() : String

    class Base(
        context: Context,
        private val mIndex: Int,
        private val mListener: (index: Int) -> Unit,
        private val mImageUrl: String
    ) : CarouselItem {
        @SuppressLint("UseCompatLoadingForDrawables")
        private val mView = FrameLayout(context).apply {
            setBackgroundResource(R.drawable.carousel_item_bg)
            if (mIndex == 0) {
                elevation = 12f
                scaleX = 1.3f
                scaleY = 1.1f
            }
            layoutParams = FrameLayout.LayoutParams(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    66f, context.resources.displayMetrics
                ).toInt(),
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    38f, context.resources.displayMetrics
                ).toInt()
            ).apply {
                this.gravity = Gravity.CENTER
                this.marginEnd = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    4f, context.resources.displayMetrics
                ).toInt()
            }
            addView(ImageView(context).apply {
                clipToOutline = true
                background = context.getDrawable(R.drawable.carousel_item_bg)
                Picasso.get().load(mImageUrl).into(this)
                setOnClickListener {
                    mListener.invoke(mIndex)
                }
                scaleType = ImageView.ScaleType.CENTER_CROP
                layoutParams = FrameLayout.LayoutParams(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        66f, context.resources.displayMetrics
                    ).toInt(),
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        38f, context.resources.displayMetrics
                    ).toInt()
                ).apply {
                    this.gravity = Gravity.CENTER
                }
            })
        }

        override fun view() = mView

        override fun url() = mImageUrl
    }
}