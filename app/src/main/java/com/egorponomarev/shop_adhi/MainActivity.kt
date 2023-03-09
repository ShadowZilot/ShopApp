package com.egorponomarev.shop_adhi

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.egorponomarev.good_detail.R.id
import com.egorponomarev.theme.base.ShopApp
import com.egorponomarev.theme.base.navigateWithoutBack
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainHostNav) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)
        bottomNav.selectedItemId = R.id.actionProfile
        bottomNav.menu.findItem(R.id.actionProfile).isChecked = true
        (application as ShopApp).initBottomNav(bottomNav)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    navController.navigateWithoutBack(R.id.toStoreFragment)
                    true
                }
                R.id.actionProfile -> {
                    navController.navigateWithoutBack(R.id.toUserProfile)
                    true
                }
                else -> false
            }
        }
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (findNavController(R.id.mainHostNav)
                        .backQueue.last().destination.id == id.detailFragment
                ) {
                    findNavController(R.id.mainHostNav).navigateWithoutBack(
                        R.id.toStoreFragment
                    )
                }
            }
        })
    }
}