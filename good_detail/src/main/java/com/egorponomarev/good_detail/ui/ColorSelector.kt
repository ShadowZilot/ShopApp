package com.egorponomarev.good_detail.ui

import android.widget.LinearLayout

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

        override fun setup(colors: List<String>) {

        }
    }
}