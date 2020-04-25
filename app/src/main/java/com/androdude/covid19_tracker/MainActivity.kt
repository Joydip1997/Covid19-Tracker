package com.androdude.covid19_tracker

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androdude.covid19_tracker.allCountryData.allCountryDataActivity
import com.androdude.covid19_tracker.indiaStateData.IndiaStateDataActivity
import com.androdude.covid19_tracker.indiaStateData.allStatedata
import com.androdude.covid19_tracker.indiaStateData.mIndiaStateAdapter
import com.androdude.covid19_tracker.topCountryUtils.mAdapter1
import com.androdude.covid19_tracker.topCountryUtils.topCountry
import com.androdude.covid19_tracker.utils.LoadingClass
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var stateName = "West Bengal"
    val pDialog = LoadingClass()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val view = findViewById(R.id.topCountryrView) as RecyclerView
        val list = ArrayList<topCountry>()



            viewAllCountryView.setOnClickListener()
            {
                var allCountryDataIntent = Intent(applicationContext,allCountryDataActivity::class.java)
                startActivity(allCountryDataIntent)
            }

        viewAllIndiaStates.setOnClickListener()
        {
            var allCountryDataIntent = Intent(applicationContext,IndiaStateDataActivity::class.java)
            startActivity(allCountryDataIntent)
        }

        val mAdapter = mAdapter1()

        view.adapter=mAdapter
        view.layoutManager = LinearLayoutManager(this)


       searchFun()


        pDialog.get(this,parentLinearLayout)
        pDialog.run()

         parse(list,mAdapter,pDialog)
         parseWorldData(pDialog)
         parseStateData()
         parseDate()


    }


    fun parseDate()
    {
        val url = "https://api.covid19india.org/data.json"

        val request = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val obj = response.getJSONArray("statewise")

                val newLine = System.getProperty("line.separator")

                val jArray = obj.getJSONObject(0)
                updateDate.setText("Last Updated On"+ newLine + jArray.getString("lastupdatedtime"))

            },
            Response.ErrorListener { error ->

            })

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }




    fun parse(list: ArrayList<topCountry>,mAdapter1: mAdapter1,pBar:LoadingClass)
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
                    list.add(topCountry(name,Integer.parseInt(totalCases),activeCases,recoveredCases,deathCases))


                }
                //Top 3 Country Status In RecyclerView
                Collections.sort(list,CustomComparator())
                mAdapter1.get(list)
                mAdapter1.notifyDataSetChanged()

                //India Status
                data1.setText(response.getJSONObject(92).getString("cases"))
                data2.setText(response.getJSONObject(92).getString("active"))
                data3.setText(response.getJSONObject(92).getString("recovered"))
                data4.setText(response.getJSONObject(92).getString("deaths"))

             //   pBar.run()

            }catch (e:Exception)
            {
                Log.i("TAG1",e.toString())
            }
        },
        Response.ErrorListener {
            error ->
            Log.i("TAG1",error.toString())
        })




        val queue = Volley.newRequestQueue(this)
        queue.add(request1)


    }

    fun splitFun(word:String)
    {
        try {
            var c = word.split(",").toTypedArray()
            stateName = c.get(1)
        }catch (e:Exception)
        {
            stateName=word
        }
        parseStateData()
    }

    fun addList() : ArrayList<String>
    {
        var suggestions: ArrayList<String> = ArrayList()
        suggestions.add("Andhra Pradesh")
        suggestions.add("Arunachal Pradesh")
        suggestions.add("Assam")
        suggestions.add("Bihar")
        suggestions.add("Chhattisgarh")
        suggestions.add("Goa")
        suggestions.add("Gujarat")
        suggestions.add("Haryana")
        suggestions.add("Himachal Pradesh")
        suggestions.add("Jharkhand")
        suggestions.add("Karnataka")
        suggestions.add("Kerala")
        suggestions.add("Madhya Pradesh")
        suggestions.add("Maharashtra")
        suggestions.add("Manipur")
        suggestions.add("Meghalaya")
        suggestions.add("Mizoram")
        suggestions.add("Nagaland")
        suggestions.add("Odisha")
        suggestions.add("Punjab")
        suggestions.add("Rajasthan")
        suggestions.add("Sikkim")
        suggestions.add("Tamil Nadu")
        suggestions.add("Telangana")
        suggestions.add("Tripura")
        suggestions.add("Uttar Pradesh")
        suggestions.add("Uttarakhand")
        suggestions.add("West Bengal")
        return suggestions
    }

    fun searchFun()
    {
        var suggestions = addList()
        val from = arrayOf("Name")
        val to = intArrayOf(android.R.id.text1)
        val suggestionAdapter: CursorAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1,
            null,from,
            to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        )
        searchView.setSuggestionsAdapter(suggestionAdapter)

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener
        {
            override fun onSuggestionSelect(position: Int): Boolean {
                return true
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val ca  = searchView.suggestionsAdapter
                val cursor = ca.cursor
                cursor.moveToPosition(position)
                searchView.setQuery(cursor.getString(cursor.getColumnIndex("Name")),false)
                return true
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String): Boolean {
                splitFun(query)

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {

                var columns = arrayOf(
                    BaseColumns._ID,
                    SearchManager.SUGGEST_COLUMN_TEXT_1
                )

                var cursor = MatrixCursor(arrayOf(BaseColumns._ID, "Name"))

                for (i in 0 until suggestions.size)
                {
                    if (suggestions.get(i).toLowerCase()
                            .startsWith(newText.toLowerCase())
                    )
                    {
                        Log.i("TAG",i.toString())
                        cursor.addRow(arrayOf<Any>(i, suggestions[i]))
                    }
                }
                suggestionAdapter.changeCursor(cursor)
                return false
            }


        })
    }

    fun parseStateData()
    {
        var flag = true
        val url = "https://api.covid19india.org/data.json"
        val request = JsonObjectRequest(Request.Method.GET,url,null,

            Response.Listener { response ->
                try {
                    val obj = response.getJSONArray("statewise")
                    for (i in 1 until obj.length())
                    {
                        var states = obj.getJSONObject(i)



                        if(states.getString("state").toLowerCase().equals(stateName.toLowerCase()))
                        {
                            stateNameView.setText(states.getString("state"))
                            statedata1.setText(states.getString("confirmed"))
                            statedata2.setText(states.getString("active"))
                            statedata3.setText(states.getString("recovered"))
                            statedata4.setText(states.getString("deaths"))
                            flag=true
                            break
                        }
                        else
                        {
                            flag = false
                        }
                    }
                    if(flag==false)
                    {
                        wrongView.visibility= View.VISIBLE

                        stateNameView.visibility= View.GONE

                        statedata1.visibility= View.GONE
                        statedata2.visibility= View.GONE
                        statedata3.visibility= View.GONE
                        statedata4.visibility= View.GONE

                        statelabel1.visibility= View.GONE
                        statelabel2.visibility= View.GONE
                        statelabel3.visibility= View.GONE
                        statelabel4.visibility= View.GONE

                    }
                    else
                    {
                        wrongView.visibility= View.GONE

                        stateNameView.visibility= View.VISIBLE

                        statedata1.visibility= View.VISIBLE
                        statedata2.visibility= View.VISIBLE
                        statedata3.visibility= View.VISIBLE
                        statedata4.visibility= View.VISIBLE

                        statelabel1.visibility= View.VISIBLE
                        statelabel2.visibility= View.VISIBLE
                        statelabel3.visibility= View.VISIBLE
                        statelabel4.visibility= View.VISIBLE

                    }

                }catch (e: Exception) {

                }

            }, Response.ErrorListener
            { error ->

            }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(request)


    }

    fun parseWorldData(pBar:LoadingClass)
    {
        val url = "https://corona.lmao.ninja/v2/all"

        val request = JsonObjectRequest(Request.Method.GET,url,null,
            Response.Listener { response ->
                val totalCase = response.getString("cases")
                val activeCase = response.getString("active")
                val recorvedCase = response.getString("recovered")
                val deathCase = response.getString("deaths")

                val todayCases = response.getString("todayCases")
                val todayDeaths = response.getString("todayDeaths")

                val newLine = System.getProperty("line.separator")


                totalCaseView.setText( "Confirmed" + newLine + totalCase)
                newTotalCasesView.setText("+" + todayCases)

                activeCaseView.setText( "Active" + newLine + activeCase)


                recorvedCaseView.setText( "Recovered" + newLine + recorvedCase)

                deathCaseView.setText( "Deaths" + newLine + deathCase)
                newDeathCasesView.setText("+" + todayDeaths)

                pBar.stop()


        },
        Response.ErrorListener { error->

        })
        val queue = Volley.newRequestQueue(this)
        queue.add(request)

    }


}



class CustomComparator : Comparator<topCountry> {
    override fun compare(o1: topCountry, o2: topCountry): Int {
        return o2.total.compareTo(o1.total)
    }
}
