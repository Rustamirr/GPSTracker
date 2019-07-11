package com.udmurtenergo.gpstracker.view.activity_main

import android.view.MenuItem
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Screen

interface MainActivityContract {

    interface View {
        var navigator: Navigator
    }
    interface Presenter {
        fun onResume(view: View)
        fun onPause()
        fun navigateTo(screen: Screen)
        fun bottomNavigationItemSelected(menuItem: MenuItem)
    }
}
