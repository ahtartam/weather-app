package ru.ahtartam.weatherapp.mvp.base


interface MvpPresenter<V : MvpView> {
    fun attachView(mvpView: V)
    fun getView(): V?
    fun viewIsReady()
    fun detachView()
    fun destroy()
}