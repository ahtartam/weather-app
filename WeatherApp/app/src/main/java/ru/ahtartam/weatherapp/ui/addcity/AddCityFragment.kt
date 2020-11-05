package ru.ahtartam.weatherapp.ui.addcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_city.*
import kotlinx.coroutines.CoroutineScope
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.mvp.AddCityContract
import ru.ahtartam.weatherapp.ui.citylist.CityListFragment
import javax.inject.Inject

class AddCityFragment : Fragment(), AddCityContract.View {

    @Inject
    lateinit var presenter: AddCityContract.Presenter

    private lateinit var searchText: EditText

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
        searchText.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(searchText.text.toString())
                true
            } else false
        })

        search_button.setOnClickListener {
            presenter.search(searchText.text.toString())
        }
    }

    override fun getScope(): CoroutineScope = lifecycleScope
    override fun showMessage(@StringRes messageResId: Int) = showMessage(getString(messageResId))

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
