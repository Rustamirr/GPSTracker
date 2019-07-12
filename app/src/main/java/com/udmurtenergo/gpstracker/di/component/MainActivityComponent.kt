package com.udmurtenergo.gpstracker.di.component

import com.udmurtenergo.gpstracker.di.ActivityScope
import com.udmurtenergo.gpstracker.di.module.ActivityModule
import com.udmurtenergo.gpstracker.view.activity_main.location_fragment.LocationFragmentPresenter
import com.udmurtenergo.gpstracker.view.activity_main.main_fragment.MainFragmentPresenter
import com.udmurtenergo.gpstracker.view.activity_main.settings_fragment.SettingsFragmentPresenter
import dagger.Subcomponent
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface MainActivityComponent {
    fun getCicerone(): Cicerone<Router>

    fun inject(presenter: MainFragmentPresenter)
    fun inject(presenter: LocationFragmentPresenter)
    //fun inject(presenter: LogFragmentPresenter)
    //fun inject(presenter: MapFragmentPresenter)
    fun inject(presenter: SettingsFragmentPresenter)
}
