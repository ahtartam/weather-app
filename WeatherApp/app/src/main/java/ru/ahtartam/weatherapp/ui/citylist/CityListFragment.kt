package ru.ahtartam.weatherapp.ui.citylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.model.CityWithWeather
import ru.ahtartam.weatherapp.mvp.CityListContract
import javax.inject.Inject

class CityListFragment : Fragment(), CityListContract.View {

    @Inject
    lateinit var presenter: CityListContract.Presenter

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as WeatherApp).appComponent.inject(this)

        presenter.attachView(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_city_list, container, false).also {
            presenter.viewIsReady()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CityListAdapter {
            presenter.onCityClicked(it)
        }
        view.findViewById<RecyclerView>(R.id.recycler).adapter = adapter
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            presenter.refresh()
        }
    }

    override fun showCityList(list: LiveData<List<CityWithWeather>>) {
        list.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            adapter.takeData(it)
        })
    }

    override fun showCityDetails(cityId: Int) {
        findNavController().navigate(
            R.id.action_CityListFragment_to_CityDetailsFragment,
            bundleOf(Pair("cityId", cityId))
        )
    }

    override fun getScope(): CoroutineScope {
        return lifecycleScope
    }

    override fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun back() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
