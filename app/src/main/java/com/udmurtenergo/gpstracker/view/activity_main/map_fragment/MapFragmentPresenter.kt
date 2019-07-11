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
    internal var repositoryLocation: RepositoryLocation? = null
    private var view: MapFragmentContract.View? = null
    private var disposable: Disposable? = null

    init {
        App.getInstance().getInjector().getMainActivityComponent().inject(this)
    }

    override fun onViewCreated(view: MapFragmentContract.View) {
        this.view = view
        view.setTitle(App.getInstance().getString(R.string.map))
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
        disposable = repositoryLocation!!.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                if (view != null) {
                    view!!.drawTrack(list)
                }
            }
    }

    private fun unSubscribeToUpdates() {
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    override fun onMapReady() {
        if (view != null) {
            view!!.setMap(true, false, true, true)

            // Check gps permission
            if (!view!!.gpsPermissionGranted()) {
                view!!.requestGpsPermission()
                return
            }
            view!!.setCurrentLocationEnabled(true)
        }
    }

    override fun onGpsRequestPermissionsResult(granted: Boolean) {
        if (view != null) {
            view!!.setCurrentLocationEnabled(granted)
        }
    }
}
