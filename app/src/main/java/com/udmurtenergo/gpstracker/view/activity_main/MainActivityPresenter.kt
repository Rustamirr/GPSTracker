package com.udmurtenergo.gpstracker.view.activity_main

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.utils.Screens
import ru.terrakok.cicerone.Screen

class MainActivityPresenter : ViewModel(), MainActivityContract.Presenter {
    private val cicerone = App.instance.injector.getMainActivityComponentInstance().getCicerone()
    private var bottomNavigationViewCurrentItemId = R.id.bottom_bar_dashboard

    override fun onResume(view: MainActivityContract.View) {
        cicerone.navigatorHolder.setNavigator(view.navigator)
    }

    override fun onPause() {
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun onCleared() {
        App.instance.injector.clearMainActivityComponent()
        super.onCleared()
    }

    override fun bottomNavigationItemSelected(menuItem: MenuItem) {
        if (bottomNavigationViewCurrentItemId != menuItem.itemId) {
            bottomNavigationViewCurrentItemId = menuItem.itemId

            var screen: Screen? = null
            when (menuItem.itemId) {
                R.id.bottom_bar_dashboard -> screen = Screens.MainScreen()
                R.id.bottom_bar_location -> screen = Screens.LocationScreen()
                R.id.bottom_bar_log -> screen = Screens.LogScreen()
                R.id.bottom_bar_map -> screen = Screens.MapScreen()
                R.id.bottom_bar_settings -> screen = Screens.SettingsScreen()
            }
            if (screen != null) navigateTo(screen)
        }
    }

    override fun navigateTo(screen: Screen) {
        cicerone.router.newRootScreen(screen)
    }
}
