package com.udmurtenergo.gpstracker.view.activity_main.location_fragment

interface LocationFragmentContract {
    interface View {
        fun setAdapter(adapter: LocationAdapter)
        fun setTitle(title: String)
    }
    interface Presenter {
        fun onViewCreated(view: View)
        fun onStart()
        fun onStop()
        fun onDestroyView()
    }
}
