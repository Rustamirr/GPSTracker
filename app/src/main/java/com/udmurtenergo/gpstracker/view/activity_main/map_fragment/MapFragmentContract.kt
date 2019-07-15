package com.udmurtenergo.gpstracker.view.activity_main.map_fragment

import com.udmurtenergo.gpstracker.database.model.FullLocation

interface MapFragmentContract {

    interface View {
        fun setTitle(title: String)
        fun setCurrentLocationEnabled(enabled: Boolean)
        fun drawTrack(list: List<FullLocation>)
        fun setMap(enableZoomControls: Boolean, enabledTiltGesture: Boolean, enableCompass: Boolean, showCurrentLocationControl: Boolean)
        fun gpsPermissionGranted(): Boolean
        fun requestGpsPermission()
    }

    interface Presenter {
        fun onViewCreated(view: View)
        fun onStart()
        fun onStop()
        fun onDestroyView()
        fun onMapReady()
        fun onGpsRequestPermissionsResult(granted: Boolean)
    }
}
