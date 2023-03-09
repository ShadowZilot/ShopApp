package com.egorponomarev.theme.base

import android.app.Application
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class ShopApp : Application() {
    private lateinit var mBottomNav: BottomNavigationView

    fun hideBottomNav() {
        mBottomNav.visibility = View.GONE
    }

    fun showBottomNav() {
        mBottomNav.visibility = View.VISIBLE
    }

    fun initBottomNav(nav: BottomNavigationView) {
        mBottomNav = nav
    }
}