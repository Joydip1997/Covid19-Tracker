package com.androdude.covid19_tracker.topCountryUtils

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androdude.covid19_tracker.R
import kotlinx.android.synthetic.main.data_layout1.view.*

class mAdapter1() : RecyclerView.Adapter<mAdapter1.mViewHolder>() {

    var list:ArrayList<topCountry> = ArrayList()


    fun get(mList:ArrayList<topCountry>)
    {
        list=mList
    }

    class mViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)
    {
        val t1 = itemview.findViewById(R.id.topCountryNameView) as TextView
        val t2 = itemview.findViewById(R.id.topCountryTotalCasesView) as TextView
        val t3 = itemview.findViewById(R.id.topCountryActiveCasesView) as TextView
        val t4 = itemview.findViewById(R.id.topCountryRecoveredCasesView) as TextView
        val t5 = itemview.findViewById(R.id.topCountryDeathCasesView) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_layout1,null,false)
        return mViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(list.size > 3){
            return 3;
        }
        else
        {
            return list.size
        }
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        val current = list[position]
        holder.t1.setText(current.name)
        holder.t2.setText(current.total.toString())
        holder.t3.setText(current.active)
        holder.t4.setText(current.recovered)
        holder.t5.setText(current.deaths)
    }
}