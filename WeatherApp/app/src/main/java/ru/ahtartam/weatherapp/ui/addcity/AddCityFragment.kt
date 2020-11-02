package ru.ahtartam.weatherapp.ui.addcity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.model.City
import ru.ahtartam.weatherapp.mvp.AddCityContract
import ru.ahtartam.weatherapp.ui.citylist.CityListFragment
import javax.inject.Inject

class AddCityFragment : Fragment(), AddCityContract.View {

    @Inject
    lateinit var presenter: AddCityContract.Presenter

    private lateinit var searchText: EditText
    private lateinit var adapter: SelectCityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as WeatherApp).appComponent.inject(this)

        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_city, container, false).also {
            presenter.viewIsReady()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchText = view.findViewById(R.id.search_city)
        searchText.addTextChangedListener {
            presenter.search(it.toString())
        }
        adapter = SelectCityAdapter {
            presenter.onCityClicked(it)
        }
        view.findViewById<RecyclerView>(R.id.recycler).adapter = adapter
    }

    override fun showCityList(list: List<City>) {
        adapter.takeData(list)
    }

    override fun getScope(): CoroutineScope = lifecycleScope

    override fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun back() {
        findNavController().navigate(
            R.id.action_AddCityFragment_to_CityListFragment,
            bundleOf(Pair(CityListFragment.ARG_IS_NEED_REFRESH, true))
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
