package com.bangkit.capstone.planitorium.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantsItem

class HomePlantAdapter(private var items: List<PlantsItem?>? = emptyList()) :
    RecyclerView.Adapter<HomePlantAdapter.PlantViewHolder>() {

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
        val item = items?.get(position)
        holder.date.text = item?.startTime ?: "No date"
        holder.plantName.text = item?.name ?: "No name"
        holder.notes.text = item?.description ?: "No description"
    }

    override fun getItemCount(): Int = items?.size!!
}