package com.example.appcentcase.UI.Detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.appcentcase.Listeners.ICityClickListener
import com.example.appcentcase.Model.ConsolidatedWeather
import com.example.appcentcase.Model.cityWeather
import com.example.appcentcase.Model.weatherLocationItem
import com.example.appcentcase.R
import com.example.appcentcase.UI.List.ListViewAdapter
import com.example.appcentcase.databinding.ItemWeatherBinding
import org.w3c.dom.Text

class DetailViewAdapter: RecyclerView.Adapter<DetailViewAdapter.DetailViewHolder>() {
    var list: List<ConsolidatedWeather>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        list?.get(position)?.let { holder.bind(it) }
    }
    class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val weatherStateName = itemView.findViewById<TextView>(R.id.weatherStateName)
        val image=itemView.findViewById<ImageView>(R.id.image)
        val minTemp=itemView.findViewById<TextView>(R.id.minTemp)
        val maxTemp=itemView.findViewById<TextView>(R.id.maxTemp)
        val theTemp=itemView.findViewById<TextView>(R.id.theTemp)
        val windSpeed=itemView.findViewById<TextView>(R.id.windSpeed)
        val humidity=itemView.findViewById<TextView>(R.id.humidity)
        val predictability=itemView.findViewById<TextView>(R.id.predictability)

        fun bind(city: ConsolidatedWeather) {
            val roundedMinTemp=String.format("%.3f",city.minTemp)
            val roundedMaxTemp=String.format("%.3f",city.maxTemp)
            val roundedTheTemp=String.format("%.3f",city.theTemp)
            val roundedSpeed=String.format("%.3f",city.windSpeed)
            weatherStateName.text="WeatherState:"+city.weatherStateName
            minTemp.text="MinTemp:"+roundedMinTemp
            maxTemp.text="MaxTemp:"+roundedMaxTemp
            theTemp.text="TheTemp:"+roundedTheTemp
            windSpeed.text="WindSpeed"+roundedSpeed
            humidity.text="Humidity:"+city.humidity
            predictability.text="Predictability"+city.predictability
        }

    }
    override fun getItemCount()=list?.size ?: 0
}