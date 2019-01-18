package com.sample.kotlintemplates.Helper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sample.kotlintemplates.Model.Intro
import com.sample.kotlintemplates.R


class CustomAdapter(var namelist: List<Intro>,context: Context) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var context : Context
    init {
        this.context=context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.listnames_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("adatper","position ::+$position")

        holder?.bindItems(namelist[position],context)
    }

    override fun getItemCount(): Int {
        return namelist.size;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(user: Intro,context: Context) {
            val textViewName = itemView.findViewById(R.id.textView) as TextView
            val contestantimg = itemView.findViewById(R.id.contensticon) as ImageView
            textViewName.text = user.name
            try {
                Glide.with(context).load(user.image).into(contestantimg)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
