package com.udmurtenergo.gpstracker.view.activity_main.location_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.database.model.LocationData
import com.udmurtenergo.gpstracker.database.model.Satellite

import java.text.SimpleDateFormat
import java.util.Locale

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    private var list: List<FullLocation>? = null
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS ZZZZZ", Locale.US)

    fun setList(list: List<FullLocation>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationAdapter.ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        var result = 0
        if (list != null) {
            result = list!!.size
        }
        return result
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.fragment_list_item_text_view)
        var textView: TextView? = null

        init {
            ButterKnife.bind(this, view)
        }

        private fun bind(fullLocation: FullLocation) {
            // Location
            val locationData = fullLocation.locationData
            var text = App.getInstance().getString(R.string.latitude) + ": " + locationData.latitude + "\n" +
                    App.getInstance().getString(R.string.longitude) + ": " + locationData.longitude + "\n" +
                    App.getInstance().getString(R.string.date) + ": " + dateFormat.format(locationData.date) + "\n" +
                    App.getInstance().getString(R.string.speed) + ": " + Math.round(locationData.speed * 3.6) + "\n" +
                    App.getInstance().getString(R.string.accuracy) + ": " + locationData.accuracy + "\n" +
                    App.getInstance().getString(R.string.altitude) + ": " + locationData.altitude + "\n" +
                    App.getInstance().getString(R.string.bearing) + ": " + locationData.bearing + "\n"

            // Satellites
            val satellites = fullLocation.satellites
            val sb = StringBuilder()
            for ((_, snr) in satellites) {
                sb.append(snr)
                sb.append(" | ")
            }
            text += App.getInstance().getString(R.string.satellites_count) + ": " + satellites.size + "\n" +
                    App.getInstance().getString(R.string.snr) + ":\n" + sb.toString()
            textView!!.setText(text)
        }
    }
}
