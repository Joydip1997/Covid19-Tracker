package com.androdude.covid19ui.UI

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.androdude.covid19_appupdate.db.ModelClass.India.IndiaResponse
import com.androdude.covid19_appupdate.db.ModelClass.WordWide.WorldWideResponseItem
import com.androdude.covid19ui.R
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var navigation : NavController
    private lateinit var response1: IndiaResponse
    private lateinit var response2: WorldWideResponseItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.website_button.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.mohfw.gov.in/"))
            startActivity(browserIntent)
        }







    }


}