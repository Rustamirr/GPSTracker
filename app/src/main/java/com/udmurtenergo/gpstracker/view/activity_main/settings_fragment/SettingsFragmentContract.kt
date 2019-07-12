package com.udmurtenergo.gpstracker.view.activity_main.settings_fragment

interface SettingsFragmentContract {
    interface View {
        fun setPreferenceSummary(preferenceKey: String, summary: String)
        fun setTitle(title: String)
    }
    interface Presenter {
        fun onViewCreated(view: View)
        fun onDestroyView()
        fun onDestroy()
        fun onSharedPreferenceChanged()
    }
}
