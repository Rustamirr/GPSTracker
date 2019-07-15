package com.udmurtenergo.gpstracker.view.activity_main.log_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.LogData

import java.text.SimpleDateFormat
import java.util.Locale

class LogAdapter : RecyclerView.Adapter<LogAdapter.ViewHolder>() {
    private var list: List<LogData>? = null
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS ZZZZZ", Locale.US)

    fun setList(list: List<LogData>) {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list!![position])
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.fragment_list_item_text_view)
        lateinit var tvLog: TextView

        init {
            ButterKnife.bind(this, view)
        }

        fun bind(logData: LogData) {
            val text = dateFormat.format(logData.date) + "\n" +
                    logData.title + "\n" +
                    logData.description
            tvLog.text = text
        }
    }
}
