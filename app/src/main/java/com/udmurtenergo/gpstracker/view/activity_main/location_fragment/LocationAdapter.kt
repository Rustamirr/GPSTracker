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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

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

    override fun getItemCount(): Int = list?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.fragment_list_item_text_view)
        lateinit var textView: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(fullLocation: FullLocation) {
            // Location
            val locationData = fullLocation.locationData
            var text = App.instance.getString(R.string.latitude) + ": " + locationData.latitude + "\n" +
                    App.instance.getString(R.string.longitude) + ": " + locationData.longitude + "\n" +
                    App.instance.getString(R.string.date) + ": " + dateFormat.format(locationData.date) + "\n" +
                    App.instance.getString(R.string.speed) + ": " + (locationData.speed * 3.6).roundToInt() + "\n" +
                    App.instance.getString(R.string.accuracy) + ": " + locationData.accuracy + "\n" +
                    App.instance.getString(R.string.altitude) + ": " + locationData.altitude + "\n" +
                    App.instance.getString(R.string.bearing) + ": " + locationData.bearing + "\n"

            // Satellites
            val sb = StringBuilder()
            fullLocation.satellites.forEach {
                sb.append(it.snr)
                sb.append(" | ")
            }
            text += App.instance.getString(R.string.satellites_count) + ": " + fullLocation.satellites.size + "\n" +
                    App.instance.getString(R.string.snr) + ":\n" + sb.toString()
            textView.setText(text)
        }
    }
}
