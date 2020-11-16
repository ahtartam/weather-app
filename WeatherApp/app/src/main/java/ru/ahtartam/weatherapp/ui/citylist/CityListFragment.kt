package ru.ahtartam.weatherapp.ui.citylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.data.db.model.Weather
import ru.ahtartam.weatherapp.presentation.mvp.CityListContract
import ru.ahtartam.weatherapp.ui.citydetails.CityDetailsFragment
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

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            findNavController().navigate(
                R.id.action_CityListFragment_to_AddCityFragment
            )
        }

        adapter = CityListAdapter(
            onCityClick = { cityId ->
                presenter.onCityClicked(cityId)
            },
            onCityDelete = { cityId ->
                presenter.onCityDelete(cityId)
            }
        )
        view.findViewById<RecyclerView>(R.id.recycler).adapter = adapter
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)
        swipeRefreshLayout.setOnRefreshListener {
            presenter.refresh()
        }
    }

    override fun onResume() {
        super.onResume()

        if (arguments?.getBoolean(ARG_IS_NEED_REFRESH, false) == true) {
            presenter.refresh()
        }
    }

    override fun showCityList(list: LiveData<List<Weather>>) {
        list.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            adapter.takeData(it)
        })
    }

    override fun onEmptyResult() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun showCityDetails(cityId: Int) {
        findNavController().navigate(
            R.id.action_CityListFragment_to_CityDetailsFragment,
            bundleOf(Pair(CityDetailsFragment.ARG_CITY_ID, cityId))
        )
    }

    override fun getScope(): CoroutineScope = lifecycleScope
    override fun showMessage(@StringRes messageResId: Int) = showMessage(getString(messageResId))

    override fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun back() {}

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    companion object {
        const val ARG_IS_NEED_REFRESH = "needRefresh"
    }
}
