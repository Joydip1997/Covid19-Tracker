package com.androdude.covid19_tracker.indiaStateData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androdude.covid19_tracker.R


class mIndiaStateAdapter : RecyclerView.Adapter< mIndiaStateAdapter.mViewHolder>() {

    private var mlist = ArrayList<allStatedata>()
    private var mlistener : onItemClickListener ?= null

    interface onItemClickListener
    {
        fun onItemClick(pos:Int)
    }

    fun OnIemClickListener(mListener : onItemClickListener)
    {
        mlistener=mListener
    }





    fun getList(list:ArrayList<allStatedata>)
    {
        mlist = list
    }

    class mViewHolder(itemView: View,mListener: onItemClickListener) : RecyclerView.ViewHolder(itemView)
    {
            val t1 = itemView.findViewById<TextView>(R.id.allStateNameView)
            val t2 = itemView.findViewById<TextView>(R.id.allStateTotalCasesView)
            val t3 = itemView.findViewById<TextView>(R.id.allStateActiveCasesView)
            val t4 = itemView.findViewById<TextView>(R.id.allStateRecoveredCasesView)
            val t5 = itemView.findViewById<TextView>(R.id.allStateDeathCasesView)

            init {
                itemView.setOnClickListener()
                {
                    if(mListener != null && adapterPosition != RecyclerView.NO_POSITION)
                    {
                        mListener.onItemClick(adapterPosition)
                    }
                }
            }

        fun bind(item:allStatedata)
        {
            t1.text = item.name
            t2.text = item.total.toString()
            t3.text = item.active
            t4.text = item.recovered
            t5.text = item.deaths
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  mIndiaStateAdapter.mViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_india_state_data,parent,false)
        return mViewHolder(view,mlistener!!)
    }

    override fun getItemCount(): Int {
       return mlist.size
    }

    override fun onBindViewHolder(holder: mIndiaStateAdapter.mViewHolder, position: Int) {
        holder.bind(mlist[position])
    }


}