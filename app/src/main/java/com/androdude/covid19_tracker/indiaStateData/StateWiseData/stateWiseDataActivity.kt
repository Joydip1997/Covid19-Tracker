package com.androdude.covid19_tracker.indiaStateData.StateWiseData

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdude.covid19_tracker.R
import com.androdude.covid19_tracker.indiaStateData.CustomComparator
import com.androdude.covid19_tracker.indiaStateData.allStatedata
import com.androdude.covid19_tracker.indiaStateData.mIndiaStateAdapter
import com.androdude.covid19_tracker.utils.LoadingClass
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_state_wise_data.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log

class stateWiseDataActivity : AppCompatActivity() {
    val mList = ArrayList<allStatedata>()
    val mAdapter = mIndiaStateAdapter()
    val pBAr = LoadingClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_wise_data)



        val intent = intent
        val stateName = intent.getStringExtra("STATENAME")
        paseStateWiseData(stateName)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mAdapter.getList(mList)
        allStateRView1.adapter=mAdapter
        allStateRView1.layoutManager=LinearLayoutManager(this)

        pBAr.get(this,parentLayout3)
        pBAr.run()



    }

    fun paseStateWiseData(stateName:String)
    {
        val url = "https://api.covid19india.org/v2/state_district_wise.json"

        val request = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->

           try {

               val JsonArray = response


                var j = 0


                     for (i in 0 until JsonArray.length())
                     {
                         val obj =JsonArray.getJSONObject(i)
                         val state = obj.getString("state")
                            if(state.equals(stateName))
                            {
                                j=i
                            }
                     }

                    val obj = JsonArray.getJSONObject(j)
                    val details = obj.getJSONArray("districtData")

                    for (i in 0 until details.length())
                    {
                        val Obj = details.getJSONObject(i)
                        val distName = Obj.getString("district")
                        val confirmed = Obj.getString("confirmed")
                        val active = Obj.getString("active")
                        val recovered = Obj.getString("recovered")
                        val deceased = Obj.getString("deceased")
                        mList.add(allStatedata(distName,Integer.parseInt(confirmed),active,recovered,deceased))
                    }

                    Collections.sort(mList,CustomComparator(0))
                    mAdapter.getList(mList)
                    mAdapter.notifyDataSetChanged()
                    pBAr.stop()


               mAdapter.OnIemClickListener(object : mIndiaStateAdapter.onItemClickListener
               {
                   override fun onItemClick(pos: Int) {
                       Log.i("Info",mList[pos].name)
                   }

               })





           }catch (e:Exception)
           {
               Log.i("Error",e.toString())
           }


        },
        Response.ErrorListener { error ->
            Log.i("Error", error.toString())
        })

        val vollyRequest = Volley.newRequestQueue(this)
        vollyRequest.add(request)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val infalter = menuInflater
        infalter.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sort1)
        {
            Collections.sort(mList,CustomComparator(0))
            mAdapter.notifyDataSetChanged()
        }
        else
        {
            Collections.sort(mList,CustomComparator(1))
            mAdapter.notifyDataSetChanged()
        }
        return super.onOptionsItemSelected(item)
    }


}


