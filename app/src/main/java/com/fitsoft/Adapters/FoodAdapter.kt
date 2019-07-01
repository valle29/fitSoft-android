package com.fitsoft.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fitsoft.R
import kotlinx.android.synthetic.main.row_day_food.view.*

class FoodAdapter(val items: ArrayList<String>): RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_day_food,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
        var tvFood = view.txtFood
        fun bindItems(food: String){
            tvFood.text = food
        }
    }
}