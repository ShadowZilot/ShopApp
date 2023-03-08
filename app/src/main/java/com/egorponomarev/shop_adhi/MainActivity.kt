package com.egorponomarev.shop_adhi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
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
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actionHome -> {
                    navController.navigate(R.id.toStoreFragment)
                    true
                }
                R.id.actionProfile -> {
                    navController.navigate(R.id.toUserProfile)
                    true
                }
                else -> false
            }
        }
    }
}