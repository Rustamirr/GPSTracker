package com.udmurtenergo.gpstracker.view.activity_main.main_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.view.BaseFragment

class MainFragment : BaseFragment(), MainFragmentContract.View {

    @BindView(R.id.fragment_main_chronometer)
    var chronometer: Chronometer? = null

    @BindView(R.id.fragment_main_tb_switch_service)
    var tbSwitchService: ToggleButton? = null

    @BindView(R.id.fragment_main_tv_location)
    var tvLocation: TextView? = null

    @BindView(R.id.fragment_main_tv_satellites)
    var tvSatellites: TextView? = null

    @BindView(R.id.fragment_main_tv_filtered_location)
    var tvFilteredLocation: TextView? = null

    private var unbinder: Unbinder? = null
    private var presenter: MainFragmentPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = ViewModelProviders.of(this).get(MainFragmentPresenter::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            tvLocation!!.text = savedInstanceState.getString(LOCATION_TEXT)
            tvSatellites!!.text = savedInstanceState.getString(SATELLITES_TEXT)
            tvFilteredLocation!!.text = savedInstanceState.getString(FILTERED_LOCATION_TEXT)
        }
        presenter!!.onViewCreated(this)
    }

    /* При повороте toggle button, если  был нажат, нажимается еще раз, чтобы этого избежать
       необходимо подписываться на прослушивание кнопки после восстановления ее нажатого состояния */
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        tbSwitchService!!.setOnCheckedChangeListener { buttonView, isChecked -> presenter!!.startStopService() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LOCATION_TEXT, tvLocation!!.text.toString())
        outState.putString(SATELLITES_TEXT, tvSatellites!!.text.toString())
        outState.putString(FILTERED_LOCATION_TEXT, tvFilteredLocation!!.text.toString())
    }

    override fun onStart() {
        super.onStart()
        presenter!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter!!.onStop()
    }

    override fun onDestroyView() {
        presenter!!.onDestroyView()
        unbinder!!.unbind()
        super.onDestroyView()
    }


    override fun setTitle(title: String) {
        if (activity != null) {
            activity!!.title = title
        }
    }

    override fun setSwitchServiceChecked(checked: Boolean) {
        tbSwitchService!!.setOnCheckedChangeListener(null)
        tbSwitchService!!.isChecked = checked
        tbSwitchService!!.setOnCheckedChangeListener { buttonView, isChecked -> presenter!!.startStopService() }
    }

    override fun resetChronometer() {
        chronometer!!.base = SystemClock.elapsedRealtime()
        chronometer!!.start()
    }

    override fun setLocationText(text: String) {
        tvLocation!!.text = text
    }

    override fun setSatellitesText(text: String) {
        tvSatellites!!.text = text
    }

    override fun setFilteredLocationText(text: String) {
        tvFilteredLocation!!.text = text
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    override fun requestPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE),
            PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    presenter!!.onRequestPermissionsResult(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
                }
            }
        }
    }

    companion object {
        private val PERMISSIONS_REQUEST_CODE = 1
        private val LOCATION_TEXT = "LOCATION_TEXT"
        private val SATELLITES_TEXT = "SATELLITES_TEXT"
        private val FILTERED_LOCATION_TEXT = "FILTERED_LOCATION_TEXT"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
