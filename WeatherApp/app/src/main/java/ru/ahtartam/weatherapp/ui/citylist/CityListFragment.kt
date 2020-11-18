package ru.ahtartam.weatherapp.ui.citylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_city_list.*
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.domain.model.City
import ru.ahtartam.weatherapp.presentation.citylist.CityListViewModel
import ru.ahtartam.weatherapp.presentation.citylist.CityListViewModel.NetworkState
import ru.ahtartam.weatherapp.ui.citydetails.CityDetailsFragment
import javax.inject.Inject

class CityListFragment : Fragment() {
    @Inject
    lateinit var viewModel: CityListViewModel

    private lateinit var adapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as WeatherApp).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.networkState.observe(viewLifecycleOwner) { state ->
            (state == NetworkState.Processing).also { isProcessing ->
                swipe_refresh.isRefreshing = isProcessing
            }
            when(state) {
                NetworkState.Success -> { }
                NetworkState.Processing -> { }
                is NetworkState.Failed -> showMessage(state.message)
            }
        }

        viewModel.weatherList.observe(viewLifecycleOwner) { weatherList ->
            adapter.takeData(weatherList)
        }

        fab.setOnClickListener {
            findNavController().navigate(
                R.id.action_CityListFragment_to_AddCityFragment
            )
        }

        adapter = CityListAdapter(
            onCityClick = { city ->
                showCityDetails(city)
            },
            onCityDelete = { city ->
                viewModel.onCityDelete(city)
            }
        )
        recycler.adapter = adapter
        swipe_refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    override fun onResume() {
        super.onResume()

        if (arguments?.getBoolean(ARG_IS_NEED_REFRESH, false) == true) {
            viewModel.refresh()
        }
    }

    private fun showCityDetails(city: City) {
        findNavController().navigate(
            R.id.action_CityListFragment_to_CityDetailsFragment,
            bundleOf(Pair(CityDetailsFragment.ARG_CITY, city))
        )
    }

    private fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        const val ARG_IS_NEED_REFRESH = "needRefresh"
    }
}
