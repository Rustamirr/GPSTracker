package com.udmurtenergo.gpstracker.utils

import androidx.fragment.app.Fragment
import com.udmurtenergo.gpstracker.view.activity_main.location_fragment.LocationFragment
import com.udmurtenergo.gpstracker.view.activity_main.main_fragment.MainFragment
import com.udmurtenergo.gpstracker.view.activity_main.settings_fragment.SettingsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = MainFragment.newInstance()
    }

    class LocationScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = LocationFragment.newInstance()
    }

    class LogScreen : SupportAppScreen() {
        //override fun getFragment(): Fragment = LogFragment.newInstance()
    }

    class MapScreen : SupportAppScreen() {
        //override fun getFragment(): Fragment = MapFragment.newInstance()
    }

    class SettingsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment = SettingsFragment.newInstance()
    }
}
