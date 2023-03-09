package com.egorponomarev.theme.base

import android.content.Context
import com.egorponomarev.theme.R
import java.lang.ref.WeakReference

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface PriceLabel {

    fun label() : String

    class Base(
        private val mContext: WeakReference<Context>,
        private val mPrice: Float
    ) : PriceLabel {

        override fun label() = if (mPrice % 1.0f != 0f) {
            mContext.get()?.getString(R.string.price_label_integer, mPrice) ?: "Error"
        } else {
            mContext.get()?.getString(R.string.price_label_float, mPrice) ?: "Error"
        }
    }
}