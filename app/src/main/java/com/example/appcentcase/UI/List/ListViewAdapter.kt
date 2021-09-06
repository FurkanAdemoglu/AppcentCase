package com.example.appcentcase.UI.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentcase.Listeners.ICityClickListener
import com.example.appcentcase.Model.weatherLocationItem
import com.example.appcentcase.R

class ListViewAdapter : RecyclerView.Adapter<ListViewAdapter.ListViewHolder>(){
    var list: List<weatherLocationItem>? = null
    private var listener: ICityClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return ListViewHolder(view)
    }
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it, listener) }
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val containerCardView = itemView.findViewById<CardView>(R.id.containerCardView)
        val textViewName = itemView.findViewById<TextView>(R.id.name)
        fun bind(city: weatherLocationItem,listener: ICityClickListener?) {
            containerCardView.setOnClickListener { listener?.onClick(city) }
            textViewName.text = city.title
        }

    }
    override fun getItemCount()= list?.size ?: 0

    fun setMovieOnClickListener(listener: ICityClickListener) {
        this.listener = listener
    }

}