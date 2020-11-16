package ru.ahtartam.weatherapp.ui.citylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.data.db.model.CityWeatherDBO

class CityListAdapter(
    private val onCityClick: (cityId: Int) -> Unit,
    private val onCityDelete: (cityId: Int) -> Unit
) : RecyclerView.Adapter<CityListAdapter.ViewHolder>() {
    private var list: List<CityWeatherDBO> = listOf()

    fun takeData(list: List<CityWeatherDBO>) {
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
            onCityClick(list[position].cityId)
        }

        val item = list[position]

        holder.textCityName.text = item.cityName
        holder.textTemperature.text = item.temperature
            ?.let { holder.view.context.getString(R.string.current_temperature, it) } ?: "N/A"

        holder.deleteButton.setOnClickListener {
            onCityDelete(item.cityId)
        }
    }

    override fun getItemCount() = list.size
}