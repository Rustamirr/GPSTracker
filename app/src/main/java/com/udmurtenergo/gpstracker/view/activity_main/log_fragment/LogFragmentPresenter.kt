/*package com.udmurtenergo.gpstracker.view.activity_main.log_fragment

import androidx.lifecycle.ViewModel
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.LogData
import com.udmurtenergo.gpstracker.repository.RepositoryLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class LogFragmentPresenter : ViewModel(), LogFragmentContract.Presenter {
    @Inject
    internal var repositoryLog: RepositoryLog? = null
    @Inject
    internal var adapter: LogAdapter? = null
    private var view: LogFragmentContract.View? = null
    private var disposable: Disposable? = null

    init {
        App.getInstance().getInjector().getMainActivityComponent().inject(this)
    }

    override fun onViewCreated(view: LogFragmentContract.View) {
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
        disposable = repositoryLog!!.getAll()
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

    private fun updateView(list: List<LogData>) {
        if (view != null) {
            var title = App.getInstance().getString(R.string.log)
            if (list.size > 0) {
                title += " (" + list.size + ")"
            }
            view!!.setTitle(title)
        }
    }
}*/
