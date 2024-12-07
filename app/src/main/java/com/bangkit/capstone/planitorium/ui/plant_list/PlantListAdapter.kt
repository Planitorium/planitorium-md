package com.bangkit.capstone.planitorium.ui.plant_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantsItem
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.Time
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlantListAdapter(private var items: List<PlantsItem?>?) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {
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
        val item = items!![position]
        holder.date.text = if (item?.startTime == null) "No Date" else item.startTime.toString()
        holder.plantName.text = if (item?.name.isNullOrBlank()) "No Name" else item?.name
        holder.notes.text = if (item?.description.isNullOrBlank()) "No Description" else item?.description

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(item!!)
        }
    }

    //OnClick Callback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PlantsItem)
    }

    override fun getItemCount(): Int = items!!.size

}