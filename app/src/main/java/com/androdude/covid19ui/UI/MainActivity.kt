package com.androdude.covid19ui.UI

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.androdude.covid19ui.R
import com.androdude.covid19ui.helper.checkInternet
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_india.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var navigation : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        if(savedInstanceState == null)
        {
            chip_menu.setItemSelected(R.id.home)
            supportFragmentManager.beginTransaction().replace(R.id.main_layout,HomeFragment()).commit()
        }

        chip_menu.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(id: Int) {
                when(id)
                {
                    R.id.home_menu ->   supportFragmentManager.beginTransaction().replace(R.id.main_layout,HomeFragment()).commit()
                    R.id.india_menu ->   supportFragmentManager.beginTransaction().replace(R.id.main_layout,IndiaFragment()).commit()
                    R.id.global_menu ->   supportFragmentManager.beginTransaction().replace(R.id.main_layout,GlobalFragment()).commit()
                }
            }

        })



    }


}