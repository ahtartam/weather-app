package ru.ahtartam.weatherapp.mvp.base


abstract class PresenterBase<V : MvpView> : MvpPresenter<V> {
    private var view: V? = null

    override fun attachView(mvpView: V) {
        view = mvpView
    }

    override fun detachView() {
        view = null
    }

    override fun getView(): V? {
        return view
    }

    protected open fun isViewAttached(): Boolean {
        return view != null
    }

    override fun destroy() {}

}