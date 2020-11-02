package ru.ahtartam.weatherapp.ui.citydetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_city_details.*
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.model.CityWithDailyForecast
import ru.ahtartam.weatherapp.model.CityWithWeather
import ru.ahtartam.weatherapp.mvp.CityDetailsContract
import java.text.SimpleDateFormat
import javax.inject.Inject

class CityDetailsFragment : Fragment(), CityDetailsContract.View {

    @Inject
    lateinit var presenter: CityDetailsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as WeatherApp).appComponent.inject(this)

        presenter.attachView(this)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        presenter.setCityId(requireArguments().getInt(ARG_CITY_ID))

        return inflater.inflate(R.layout.fragment_city_details, container, false).also {
            presenter.viewIsReady()
        }
    }

    override fun showCityDetails(info: LiveData<CityWithWeather>) {
        info.observe(viewLifecycleOwner, Observer {
            city_name.text = it.city.name
            current_temperature.text = getString(R.string.current_temperature, it.weather?.temperature)
        })
    }

    override fun showDailyForecast(forecast: LiveData<CityWithDailyForecast>) {
        forecast.observe(viewLifecycleOwner, Observer { value ->
            daily_forecast.text = value.forecastList?.joinToString("\n") {
                "${SimpleDateFormat.getDateInstance().format(it.date)}  --  ${it.temperature}"
            }
        })
    }

    override fun getScope(): CoroutineScope = lifecycleScope

    override fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun back() {
        findNavController().navigate(R.id.action_CityDetailsFragment_to_CityListFragment)
    }

    companion object {
        const val ARG_CITY_ID = "cityId"
    }
}
