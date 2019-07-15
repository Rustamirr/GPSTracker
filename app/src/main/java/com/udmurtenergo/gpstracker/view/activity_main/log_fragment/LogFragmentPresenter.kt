package com.udmurtenergo.gpstracker.view.activity_main.log_fragment

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
    lateinit var repositoryLog: RepositoryLog
    @Inject
    lateinit var adapter: LogAdapter
    private var view: LogFragmentContract.View? = null
    private lateinit var disposable: Disposable

    init {
        App.instance.injector.getMainActivityComponentInstance().inject(this)
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
        disposable = repositoryLog.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                adapter.setList(list)
                adapter.notifyDataSetChanged()
                updateView(list)
            }
    }

    private fun unSubscribeToUpdates() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun updateView(list: List<LogData>) {
        if (view == null) return

        var title = App.instance.getString(R.string.log)
        if (list.isNotEmpty()) {
            title += " (" + list.size + ")"
        }
        view?.setTitle(title)
    }
}
