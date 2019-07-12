package com.udmurtenergo.gpstracker.view.activity_main.location_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.view.BaseFragment

class LocationFragment : BaseFragment(), LocationFragmentContract.View {
    companion object {
        fun newInstance() = LocationFragment()
    }
    @BindView(R.id.fragment_list_recycler_view)
    lateinit var recyclerView: RecyclerView

    private lateinit var presenter: LocationFragmentContract.Presenter
    private lateinit var unbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ViewModelProviders.of(this).get(LocationFragmentPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        unbinder = ButterKnife.bind(this, view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onViewCreated(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        unbinder.unbind()
        super.onDestroyView()
    }

    override fun setTitle(title: String) {
        activity?.title = title
    }

    override fun setAdapter(adapter: LocationAdapter) {
        recyclerView.adapter = adapter
    }
}
