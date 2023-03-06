package com.egorponomarev.theme.base

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */

fun NavController.navigateWithoutBack(
    action: Int, args: Bundle? = null
) {
    navigate(action, args, NavOptions.Builder().apply {
        if (backQueue.isNotEmpty()) {
            setPopUpTo(
                backQueue.last().destination.id,
                true
            )
        }
    }.build())
}