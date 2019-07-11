/*package com.udmurtenergo.gpstracker.view.activity_main.settings_fragment

import androidx.lifecycle.ViewModel
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.interactor.PreferenceInteractor
import com.udmurtenergo.gpstracker.utils.Settings
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class SettingsFragmentPresenter : ViewModel(), SettingsFragmentContract.Presenter {
    @Inject
    internal var preferenceInteractor: PreferenceInteractor? = null
    private var view: SettingsFragmentContract.View? = null
    private var settings: Settings? = null
    private var disposable: Disposable? = null

    init {
        App.getInstance().getInjector().getMainActivityComponent().inject(this)
        loadSettings()
    }

    override fun onViewCreated(view: SettingsFragmentContract.View) {
        this.view = view
        view.setTitle(App.getInstance().getString(R.string.settings))
        updateView()
    }

    override fun onDestroyView() {
        view = null
    }

    override fun onDestroy() {
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    private fun loadSettings() {
        disposable = preferenceInteractor!!.load()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result ->
                settings = result
                updateView()
            }
    }

    private fun updateView() {
        if (view != null && settings != null) {
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_gps_update_interval),
                settings!!.updateInterval.toString() + " (sec)"
            )
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_smallest_displacement),
                settings!!.smallestDisplacement.toString() + " (m)"
            )
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_min_accuracy),
                settings!!.minAccuracy.toString() + " (m)"
            )
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_min_satellites_count),
                settings!!.minSatellitesCount.toString()
            )
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_min_snr),
                settings!!.minSnr.toString()
            )
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_server_ip),
                settings!!.serverIp
            )
            view!!.setPreferenceSummary(
                App.getInstance().getString(R.string.key_network_update_interval),
                settings!!.networkUpdateInterval.toString() + " (min)"
            )
        }
    }

    override fun onSharedPreferenceChanged() {
        loadSettings()
    }
}*/
