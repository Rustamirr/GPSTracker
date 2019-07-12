package com.udmurtenergo.gpstracker.view.activity_main.main_fragment

interface MainFragmentContract {
    interface View {
        fun setTitle(title: String)
        fun setSwitchServiceChecked(checked: Boolean)
        fun setLocationText(text: String)
        fun setSatellitesText(text: String)
        fun setFilteredLocationText(text: String)
        fun resetChronometer()
        fun showMessage(message: String)
        fun requestPermissions()
    }
    interface Presenter {
        fun onViewCreated(view: View)
        fun onStart()
        fun onStop()
        fun onDestroyView()
        fun startStopService()
        fun onRequestPermissionsResult(granted: Boolean)
    }
}
