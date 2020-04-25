package com.androdude.covid19_tracker.introScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androdude.covid19_tracker.R

class mIntroAdapter(val list:ArrayList<introItems>) : RecyclerView.Adapter<mIntroAdapter.mIntroItemViewHolder>() {



    class mIntroItemViewHolder(itemview: View):RecyclerView.ViewHolder(itemview)
    {
        val t1 = itemview.findViewById<ImageView>(R.id.imageView)
        val t2 = itemview.findViewById<TextView>(R.id.caption)
        val t3 = itemview.findViewById<TextView>(R.id.details)

        fun bind(item: introItems)
        {
            t1.setImageResource(item.icon)
            t2.text = item.caption
            t3.text = item.details
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mIntroItemViewHolder {
        return mIntroItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.intro_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mIntroItemViewHolder, position: Int) {
        holder.bind(list[position])
    }
}