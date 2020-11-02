package ru.ahtartam.weatherapp.mvp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ahtartam.weatherapp.db.Database
import ru.ahtartam.weatherapp.db.DatabaseProvider
import ru.ahtartam.weatherapp.mvp.base.PresenterBase
import javax.inject.Inject

class CityListPresenter @Inject constructor(
    dbProvider: DatabaseProvider
) : PresenterBase<CityListContract.View>(), CityListContract.Presenter {
    private val db: Database = dbProvider.get()

    override fun viewIsReady() {
        getView().getScope().launch(Dispatchers.IO) {

        }
    }

    override fun onCityClicked(cityId: Int) {
        getView().showCityDetails(cityId)
    }
}