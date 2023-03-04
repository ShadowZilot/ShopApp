package com.egorponomarev.theme.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
abstract class BaseFragment<T : ViewBinding>(
    private val mResId: Int
) : Fragment() {

    protected abstract val mBinding : T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(mResId, container, false)
}