package com.androdude.covid19_tracker.allCountryData

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdude.covid19_tracker.CustomComparator
import com.androdude.covid19_tracker.MainActivity
import com.androdude.covid19_tracker.R
import com.androdude.covid19_tracker.topCountryUtils.mAdapter1
import com.androdude.covid19_tracker.topCountryUtils.topCountry
import com.androdude.covid19_tracker.utils.LoadingClass
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_all_country_data.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.lang.reflect.Type
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class allCountryDataActivity : AppCompatActivity() {
    var list = ArrayList<allCountryData>()
    val mAdapter = mAllCountryAdapter()
    val pBar = LoadingClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_country_data)




        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        pBar.get(this,parentLayout1)
        pBar.run()

        parse(list,mAdapter,this,pBar)

        mAdapter.getList(list)
        allCountryrView.adapter=mAdapter
        allCountryrView.layoutManager=LinearLayoutManager(this)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sort1)
        {
            Collections.sort(list,CoustomComparator(0))
            mAdapter.notifyDataSetChanged()
        }
        else
        {
            Collections.sort(list,CoustomComparator(1))
            mAdapter.notifyDataSetChanged()
        }
        return super.onOptionsItemSelected(item)
    }



}

fun parse(list: ArrayList<allCountryData>,mAdapter1: mAllCountryAdapter,context: Context,pBar:LoadingClass)
{
    val url = "https://corona.lmao.ninja/v2/countries"
    var i = 0
    val request1 = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener {
            response ->
        try {

            for(i in 0 until response.length())
            {
                val obj = response.getJSONObject(i)
                val name = obj.getString("country")
                val totalCases = obj.getString("cases")
                val activeCases = obj.getString("active")
                val recoveredCases = obj.getString("recovered")
                val deathCases = obj.getString("deaths")
                list.add(allCountryData(name,Integer.parseInt(totalCases),activeCases,recoveredCases,deathCases))


            }
            //All Country Status In RecyclerView
            Collections.sort(list,CoustomComparator(1))
            mAdapter1.getList(list)
            mAdapter1.notifyDataSetChanged()

            pBar.stop()





        }catch (e:Exception)
        {
            Log.i("TAG1",e.toString())
        }
    },
        Response.ErrorListener {
                error ->
            Log.i("TAG1",error.toString())
        })

    val queue = Volley.newRequestQueue(context)
    queue.add(request1)
}


class CoustomComparator(val i:Int) : Comparator<allCountryData>
{
    override fun compare(o1: allCountryData, o2: allCountryData): Int {
        if(i==0)
        {
            return (o1.total.compareTo(o2.total))
        }
        else
        {
            return (o2.total.compareTo(o1.total))
        }
    }

}

