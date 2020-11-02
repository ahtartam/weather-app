package ru.ahtartam.weatherapp.mvp

import kotlinx.coroutines.*
import ru.ahtartam.weatherapp.db.Database
import ru.ahtartam.weatherapp.db.DatabaseProvider
import ru.ahtartam.weatherapp.model.Weather
import ru.ahtartam.weatherapp.mvp.base.PresenterBase
import javax.inject.Inject

class AddCityPresenter @Inject constructor(
    dbProvider: DatabaseProvider
) : PresenterBase<AddCityContract.View>(), AddCityContract.Presenter {
    private val db: Database = dbProvider.get()

    override fun viewIsReady() {
        search("")
    }

    override fun onCityClicked(cityId: Int) {
        getView()?.getScope()?.launch(Dispatchers.IO) {
            db.weatherDao().upsert(listOf(Weather(cityId)))
            withContext(Dispatchers.Main) {
                getView()?.back()
            }
        }
    }

    override fun search(text: String) {
        getView()?.getScope()?.launch(Dispatchers.IO) {
            val list = db.cityDao().searchCityList("%$text%")
            withContext(Dispatchers.Main) {
                getView()?.showCityList(list)
            }
        }
    }
}