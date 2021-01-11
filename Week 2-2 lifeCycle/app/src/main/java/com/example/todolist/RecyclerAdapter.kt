package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(val context: Context) : RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder>(){

    var mList=ArrayList<List_item>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.CustomViewHolder, position: Int) {
        holder.s_date.text=mList.get(position).date
        holder.s_memo.text=mList.get(position).memo
    }

    class CustomViewHolder(itemView : View):RecyclerView.ViewHolder(itemView) {
        val s_date = itemView.findViewById<TextView>(R.id.selected_day)
        val s_memo = itemView.findViewById<TextView>(R.id.list_memo)
    }

}