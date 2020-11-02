package ru.ahtartam.weatherapp.ui.citylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.model.CityWithWeather

class CityListAdapter(
    private val onCityClick: (cityId: Int) -> Unit
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var list: List<CityWithWeather> = listOf()

    fun takeData(list: List<CityWithWeather>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {
        val textCityName: TextView = view.findViewById(R.id.city_name)
        val textTemperature: TextView = view.findViewById(R.id.city_temperature)
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
            onCityClick(list[position].city.id)
        }
        holder.textCityName.text = list[position].city.name
        val temperature = list[position].weather?.temperature
        holder.textTemperature.text = if (temperature != null)
            holder.view.context.getString(R.string.current_temperature, temperature)
        else "N/A"
    }

    override fun getItemCount() = list.size
}