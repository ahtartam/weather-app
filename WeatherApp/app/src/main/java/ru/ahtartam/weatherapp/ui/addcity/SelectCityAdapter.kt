package ru.ahtartam.weatherapp.ui.addcity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.model.City

class SelectCityAdapter(
    private val onCityClick: (cityId: Int) -> Unit
) : RecyclerView.Adapter<SelectCityAdapter.ViewHolder>() {
    private var list: List<City> = listOf()

    fun takeData(list: List<City>) {
        this.list = list
        notifyDataSetChanged()
    }

    class ViewHolder(
        val view: View
    ) : RecyclerView.ViewHolder(view) {
        val textCityName: TextView = view.findViewById(R.id.city_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_city, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener {
            onCityClick(list[position].id)
        }
        holder.textCityName.text = list[position].name
    }

    override fun getItemCount() = list.size
}