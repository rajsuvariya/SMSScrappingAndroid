package com.rajsuvariya.smsscrapping

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by @raj on 27/08/18.
 */
class SMSAdapter(var mList: ArrayList<String>) : RecyclerView.Adapter<SMSAdapter.SMSViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SMSViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sms_item, parent, false)
        return SMSViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: SMSViewHolder, position: Int) {
        holder.textView.text = mList[position]
    }

    class SMSViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView!!.findViewById<TextView>(R.id.textView)!!
    }
}