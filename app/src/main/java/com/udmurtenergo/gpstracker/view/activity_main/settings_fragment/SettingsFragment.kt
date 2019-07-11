/*package com.udmurtenergo.gpstracker.view.activity_main.settings_fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.udmurtenergo.gpstracker.R

class SettingsFragment : PreferenceFragmentCompat(), SettingsFragmentContract.View,
    SharedPreferences.OnSharedPreferenceChangeListener {
    private var presenter: SettingsFragmentContract.Presenter? = null

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        presenter = ViewModelProviders.of(this).get(SettingsFragmentPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter!!.onViewCreated(this)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroyView() {
        presenter!!.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter!!.onDestroy()
        super.onDestroy()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        presenter!!.onSharedPreferenceChanged()
    }

    override fun setPreferenceSummary(preferenceKey: String, summary: String) {
        val preference = findPreference<Preference>(preferenceKey)
        if (preference != null) {
            preference.summary = summary
        }
    }

    override fun setTitle(title: String) {
        if (activity != null) {
            activity!!.title = title
        }
    }

    companion object {

        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}*/
