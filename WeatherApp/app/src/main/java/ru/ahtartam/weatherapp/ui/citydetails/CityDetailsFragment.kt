package ru.ahtartam.weatherapp.ui.citydetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_city_details.*
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.network.NetworkLiveData
import ru.ahtartam.weatherapp.presentation.citydetails.CityDetailsViewModel
import ru.ahtartam.weatherapp.presentation.citydetails.CityDetailsViewModel.NetworkState
import java.text.SimpleDateFormat
import javax.inject.Inject

class CityDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModel: CityDetailsViewModel

    private lateinit var city: City

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        city = requireArguments().getParcelable(ARG_CITY)!!
        (requireContext().applicationContext as WeatherApp).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        NetworkLiveData(requireContext()).observe(viewLifecycleOwner) { available ->
            if (available) viewModel.refresh(city)
        }

        viewModel.networkState.observe(viewLifecycleOwner) { state ->
            progress.isVisible = state == NetworkState.Processing
            when(state) {
                NetworkState.Success -> { }
                NetworkState.Processing -> { }
                is NetworkState.Failed -> showMessage(state.message)
            }
        }

        viewModel.getWeather(city).observe(viewLifecycleOwner) { weather ->
            city_name.text = weather.city.cityName
            current_temperature.text = weather.weather.temperature
                ?.let { getString(R.string.current_temperature, it) } ?: "N/A"
        }

        viewModel.getForecast(city).observe(viewLifecycleOwner) { forecast ->
            daily_forecast.text = forecast.forecastList.joinToString("\n") {
                "${SimpleDateFormat.getDateInstance().format(it.date)}      ${getString(R.string.temperature, it.weather.temperature)}"
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refresh(city)
    }

    private fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun back() {
        findNavController().navigate(R.id.action_CityDetailsFragment_to_CityListFragment)
    }

    companion object {
        const val ARG_CITY = "city"
    }
}
