package ru.ahtartam.weatherapp.ui.citylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.domain.model.CityWeather

class CityListAdapter(
    private val onCityClick: (city: City) -> Unit,
    private val onCityDelete: (city: City) -> Unit
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var list: List<CityWeather> = listOf()

    fun takeData(list: List<CityWeather>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {
        val textCityName: TextView = view.findViewById(R.id.city_name)
        val textTemperature: TextView = view.findViewById(R.id.city_temperature)
        val deleteButton: View = view.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_city_weather, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener {
            onCityClick(list[position].city)
        }

        val item = list[position]

        holder.textCityName.text = item.city.cityName
        holder.textTemperature.text = item.weather.temperature
            ?.let { holder.view.context.getString(R.string.current_temperature, it) } ?: "N/A"

        holder.deleteButton.setOnClickListener {
            onCityDelete(item.city)
        }
    }

    override fun getItemCount() = list.size
}