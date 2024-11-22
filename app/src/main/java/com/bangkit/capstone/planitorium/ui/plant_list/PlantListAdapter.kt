package com.bangkit.capstone.planitorium.ui.plant_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.model.PlantItem

class PlantListAdapter(private val items: List<PlantItem>) : RecyclerView.Adapter<PlantListAdapter.PlantViewHolder>() {

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
        holder.date.text = item.date
        holder.plantName.text = item.plantName
        holder.notes.text = item.notes
    }

    override fun getItemCount(): Int = items.size
}