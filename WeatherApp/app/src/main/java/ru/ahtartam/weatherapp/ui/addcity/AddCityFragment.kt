package ru.ahtartam.weatherapp.ui.addcity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_city.*
import ru.ahtartam.weatherapp.R
import ru.ahtartam.weatherapp.WeatherApp
import ru.ahtartam.weatherapp.presentation.addcity.AddCityViewModel
import ru.ahtartam.weatherapp.presentation.addcity.AddCityViewModel.SearchState
import ru.ahtartam.weatherapp.ui.citylist.CityListFragment
import javax.inject.Inject

class AddCityFragment : Fragment() {
    @Inject
    lateinit var viewModel: AddCityViewModel

    private lateinit var searchText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireContext().applicationContext as WeatherApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.searchState.observe(viewLifecycleOwner) { state ->
            (state == SearchState.Processing).also { isProcessing ->
                progress.isVisible = isProcessing
                search_button.isInvisible = isProcessing
            }
            when(state) {
                SearchState.Success -> back()
                SearchState.Processing -> { }
                SearchState.FailedNotFound -> showMessage(R.string.city_not_found)
                is SearchState.Failed -> showMessage(state.message)
            }
        }

        searchText = view.findViewById(R.id.search_city)
        searchText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId in setOf(EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_UNSPECIFIED)) {
                viewModel.search(searchText.text.toString())
                true
            } else false
        }

        searchText.requestFocus()
        getSystemService(requireContext(), InputMethodManager::class.java)
            ?.showSoftInput(searchText, SHOW_IMPLICIT)

        search_button.setOnClickListener {
            viewModel.search(searchText.text.toString())
        }
    }

    private fun showMessage(@StringRes messageResId: Int) = showMessage(getString(messageResId))

    private fun showMessage(message: String) {
        view?.post {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun back() {
        findNavController().navigate(
            R.id.action_AddCityFragment_to_CityListFragment,
            bundleOf(Pair(CityListFragment.ARG_IS_NEED_REFRESH, true))
        )
    }
}
