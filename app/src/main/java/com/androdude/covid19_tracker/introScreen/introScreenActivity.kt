package com.androdude.covid19_tracker.introScreen

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.androdude.covid19_tracker.MainActivity
import com.androdude.covid19_tracker.R
import kotlinx.android.synthetic.main.activity_intro_screen.*

class introScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        val vPager = findViewById<ViewPager2>(R.id.viewPager)
        val list = ArrayList<introItems>()

        list.add(introItems(R.drawable.headache,"Headache","If you have strong Headache then you should definitely visit your doctor"))
        list.add(introItems(R.drawable.fever,"Fever","If you have Runny Nose then you should definitely visit your doctor"))
        list.add(introItems(R.drawable.runnynose,"Runny Nose","If you have high Fever then you should definitely visit your doctor"))

        val mAdapter = mIntroAdapter(list)

        vPager.adapter=mAdapter

        val size = list.size

        val sharedPreferences = applicationContext.getSharedPreferences("MY_SHARED_PREF", Context.MODE_PRIVATE)

        val flag = checkStatrup(sharedPreferences)
        if(flag==true)
        {
            this.finish()
            startActivity(Intent(this,MainActivity::class.java))
        }

        nextIntro.setOnClickListener()
        {
            if(vPager.currentItem +1 < size)
            {
                vPager.currentItem++
            }
            else
            {
                vPager.currentItem=0
            }
        }
        skipIntro.setOnClickListener()
        {
            startUp(sharedPreferences)
            this.finish()
            startActivity(Intent(this,MainActivity::class.java))

        }
    }

    fun startUp(sharedPreferences: SharedPreferences)
    {
        sharedPreferences.edit().putBoolean("first",true).apply()

    }

    fun checkStatrup(sharedPreferences: SharedPreferences) : Boolean
    {
        return sharedPreferences.getBoolean("first",false)
    }

}

