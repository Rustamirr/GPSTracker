/*package com.udmurtenergo.gpstracker.view.activity_main.location_fragment

import androidx.lifecycle.ViewModel
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.repository.RepositoryLocation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class LocationFragmentPresenter : ViewModel(), LocationFragmentContract.Presenter {
    @Inject
    internal var repositoryLocation: RepositoryLocation? = null
    @Inject
    internal var adapter: LocationAdapter? = null
    private var view: LocationFragmentContract.View? = null
    private var disposable: Disposable? = null

    init {
        App.getInstance().getInjector().getMainActivityComponent().inject(this)
    }

    override fun onViewCreated(view: LocationFragmentContract.View) {
        this.view = view
        view.setAdapter(adapter)
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
                adapter!!.setList(list)
                adapter!!.notifyDataSetChanged()
                updateView(list)
            }
    }

    private fun unSubscribeToUpdates() {
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }

    private fun updateView(list: List<FullLocation>) {
        if (view != null) {
            var title = App.getInstance().getString(R.string.location)
            if (list.size > 0) {
                title += " (" + list.size + ")"
            }
            view!!.setTitle(title)
        }
    }
}*/
