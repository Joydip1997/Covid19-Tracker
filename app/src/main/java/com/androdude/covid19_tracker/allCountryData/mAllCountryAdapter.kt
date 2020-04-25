package com.androdude.covid19_tracker.allCountryData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androdude.covid19_tracker.R
import com.androdude.covid19_tracker.topCountryUtils.mAdapter1

class mAllCountryAdapter : RecyclerView.Adapter<mAllCountryAdapter.mViewHolder>() {


    private var mlist = ArrayList<allCountryData>()

    fun getList(list:ArrayList<allCountryData>)
    {
        mlist=list
    }

    class mViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val t1 = itemview.findViewById(R.id.allCountryNameView) as TextView
        val t2 = itemview.findViewById(R.id.allCountryTotalCasesView) as TextView
        val t3 = itemview.findViewById(R.id.allCountryActiveCasesView) as TextView
        val t4 = itemview.findViewById(R.id.allCountryRecoveredCasesView) as TextView
        val t5 = itemview.findViewById(R.id.allCountryDeathCasesView) as TextView

        fun bind(current:allCountryData)
        {
            t1.setText(current.name)
            t2.setText(current.total.toString())
            t3.setText(current.active)
            t4.setText(current.recovered)
            t5.setText(current.deaths)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_country_data,parent,false)
        return mViewHolder(view)
    }

    override fun getItemCount(): Int {
       return mlist.size
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        holder.bind(mlist[position])
    }
}