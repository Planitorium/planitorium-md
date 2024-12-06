package com.bangkit.capstone.planitorium.ui.plant_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.data.remote.response.PlantsItem
import com.bangkit.capstone.planitorium.data.remote.response.StartTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlantListAdapter(private val items: List<PlantsItem>) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    class PlantViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val date: TextView = view.findViewById(R.id.date)
        val plantName: TextView = view.findViewById(R.id.plant_name)
        val notes: TextView = view.findViewById(R.id.notes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_item, parent, false)
        return PlantViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        val item = items[position]
        holder.date.text = if (item.startTime == null) "No Date" else convertTimestampToDate(item.startTime)
        holder.plantName.text = if (item.name.isNullOrBlank()) "No Name" else item.name
        holder.notes.text = if (item.description.isNullOrBlank()) "No Description" else item.description
        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(item)
        }
    }

    //OnClick Callback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PlantsItem)
    }

    override fun getItemCount(): Int = items.size

    private fun convertTimestampToDate(time: StartTime?): String? {
        // Ensure the time object and seconds are not null
        if (time?.seconds == null) return null

        // Convert seconds to milliseconds
        val secondsInMillis = time.seconds * 1000L

        // Convert nanoseconds to milliseconds (if available)
        val nanosecondsInMillis = (time.nanoseconds ?: 0) / 1_000_000L

        // Combine both to get total milliseconds
        val totalMillis = secondsInMillis + nanosecondsInMillis

        // Convert to Date and format
        val date = Date(totalMillis)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}