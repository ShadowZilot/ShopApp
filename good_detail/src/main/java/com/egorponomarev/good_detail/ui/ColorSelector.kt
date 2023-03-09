package com.egorponomarev.good_detail.ui

import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.core.graphics.toColorInt

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface ColorSelector {

    fun setup(colors: List<String>)

    class Base(
        private val mContainer: LinearLayout
    ) : ColorSelector {
        private val mItems = mutableListOf<View>()
        private val mDrawables = mutableListOf<GradientDrawable>()
        private var mCurrentColor = 0

        override fun setup(colors: List<String>) {
            for (i in colors.indices) {
                mDrawables.add(
                    GradientDrawable().apply {
                        color = ColorStateList.valueOf(colors[i].toColorInt())
                        cornerRadius = 12f
                    }
                )
                mItems.add(
                    View(mContainer.context).apply {
                        background = mDrawables[i]
                        elevation = 10f
                        setOnClickListener {
                            onSelectColor(i)
                        }
                        layoutParams = LinearLayout.LayoutParams(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                34f, mContainer.resources.displayMetrics
                            ).toInt(),
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                26f, mContainer.resources.displayMetrics
                            ).toInt(),
                        ).apply {
                            this.marginEnd = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                14f, mContainer.resources.displayMetrics
                            ).toInt()
                        }
                    }
                )
                mContainer.addView(mItems[i])
            }
        }

        private fun onSelectColor(index: Int) {
            mDrawables[mCurrentColor].setStroke(0, "#ADADAD".toColorInt())
            mItems[mCurrentColor].invalidate()
            mCurrentColor = index
            mDrawables[mCurrentColor].setStroke(5, "#ADADAD".toColorInt())
            mItems[mCurrentColor].invalidate()
        }
    }
}