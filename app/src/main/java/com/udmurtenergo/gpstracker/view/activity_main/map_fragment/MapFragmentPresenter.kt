package com.udmurtenergo.gpstracker.view.activity_main.map_fragment

import androidx.lifecycle.ViewModel
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.repository.RepositoryLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class MapFragmentPresenter : ViewModel(), MapFragmentContract.Presenter {
    @Inject
    lateinit var repositoryLocation: RepositoryLocation
    private var view: MapFragmentContract.View? = null
    private lateinit var disposable: Disposable

    init {
        App.instance.injector.getMainActivityComponentInstance().inject(this)
    }

    override fun onViewCreated(view: MapFragmentContract.View) {
        this.view = view
        view.setTitle(App.instance.getString(R.string.map))
    }

    override fun onStart() {
        subscribeToUpdates()
    }

    override fun onStop() {
        unSubscribeToUpdates()
    }

    override fun onDestroyView() {
        view = null
    }

    private fun subscribeToUpdates() {
        disposable = repositoryLocation.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->  view?.drawTrack(list) }
    }

    private fun unSubscribeToUpdates() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun onMapReady() {
        if (view != null) {
            val view = view as MapFragmentContract.View

            view.setMap(enableZoomControls = true, enabledTiltGesture = false, enableCompass = true, showCurrentLocationControl = true)

            // Check gps permission
            if (!view.gpsPermissionGranted()) {
                view.requestGpsPermission()
                return
            }
            view.setCurrentLocationEnabled(true)
        }
    }

    override fun onGpsRequestPermissionsResult(granted: Boolean) {
        view?.setCurrentLocationEnabled(granted)
    }
}
