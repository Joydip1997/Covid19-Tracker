package com.androdude.covid19_tracker.indiaStateData

import android.content.Intent
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.text.Layout
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androdude.covid19_tracker.R
import com.androdude.covid19_tracker.indiaStateData.StateWiseData.stateWiseDataActivity
import com.androdude.covid19_tracker.utils.LoadingClass
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_india_state.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class IndiaStateDataActivity : AppCompatActivity() {
    val stateList = ArrayList<allStatedata>()
    val mAdapter = mIndiaStateAdapter()
    val pBar = LoadingClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_india_state)



        pBar.get(this,parentLayout2)
        pBar.run()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        mAdapter.getList(stateList)

        allStateRView.adapter=mAdapter
        allStateRView.layoutManager=LinearLayoutManager(this)
        parse(stateList,mAdapter)


        mAdapter.OnIemClickListener(object : mIndiaStateAdapter.onItemClickListener
        {
            override fun onItemClick(pos: Int) {
                val intent = Intent(applicationContext,stateWiseDataActivity::class.java)
                intent.putExtra("STATENAME",stateList[pos].name)
                startActivity(intent)
            }

        })

    }

    fun parse(mlist:ArrayList<allStatedata>,mAdapter:mIndiaStateAdapter)
    {
        val url = "https://api.covid19india.org/data.json"

        val request = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val obj = response.getJSONArray("statewise")
                for (i in 1 until obj.length())
                {
                    val jArray = obj.getJSONObject(i)

                    val name = jArray.getString("state")
                    val totalCases = jArray.getString("confirmed")
                    val activeCases = jArray.getString("active")
                    val recoveredCases = jArray.getString("recovered")
                    val deaths = jArray.getString("deaths")
                    mlist.add(allStatedata(name,Integer.parseInt(totalCases),activeCases,recoveredCases,deaths))
                }

                Collections.sort(mlist,CustomComparator(1))
                mAdapter.notifyDataSetChanged()
                pBar.stop()

            },
            Response.ErrorListener { error ->

        })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.sort1)
        {
            Collections.sort(stateList,CustomComparator(0))
            mAdapter.notifyDataSetChanged()
        }
        else
        {
            Collections.sort(stateList,CustomComparator(1))
            mAdapter.notifyDataSetChanged()
        }
        return super.onOptionsItemSelected(item)
    }
}

class CustomComparator(val i:Int) : Comparator<allStatedata>
{
    override fun compare(o1: allStatedata, o2: allStatedata): Int {
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