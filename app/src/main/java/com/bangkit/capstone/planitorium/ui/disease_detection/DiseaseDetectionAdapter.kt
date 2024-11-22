package com.bangkit.capstone.planitorium.ui.disease_detection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.model.DiseaseItem

class DiseaseDetectionAdapter(private val diseaseList: List<DiseaseItem>) : RecyclerView.Adapter<DiseaseDetectionAdapter.DiseaseViewHolder>() {

    // ViewHolder class that binds the data
    class DiseaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.checked_date)
        val plantName: TextView = itemView.findViewById(R.id.disease_name)
        val healthyStatus: TextView = itemView.findViewById(R.id.healthy)
        val imageView: ImageView = itemView.findViewById(R.id.disease_image)
    }

    // Inflates the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.disease_detection_item, parent, false)
        return DiseaseViewHolder(view)
    }

    // Binds the data from the DiseaseItem object to the ViewHolder
    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val diseaseItem = diseaseList[position]

        holder.date.text = diseaseItem.date
        holder.plantName.text = diseaseItem.plantName
        val healthyString = holder.itemView.context.getString(R.string.healthy, diseaseItem.healthy.toString())
        holder.healthyStatus.text = healthyString
        holder.imageView.setImageResource(R.drawable.placeholder)
    }

    // Return the total number of items in the list
    override fun getItemCount(): Int {
        return diseaseList.size
    }
}
