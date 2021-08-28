package com.example.appyhour

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myNav = this.findNavController(R.id.nav_host_fragment)
        appBarConfiguration =AppBarConfiguration(myNav.graph)
    }

    override fun onSupportNavigateUp(): Boolean {
        val myNav =this.findNavController(R.id.nav_host_fragment)
        return NavigationUI.navigateUp(myNav, appBarConfiguration)
    }
}