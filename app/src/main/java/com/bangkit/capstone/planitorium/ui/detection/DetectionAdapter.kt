package com.bangkit.capstone.planitorium.ui.detection

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.remote.response.detection.DetectionsItem
import com.bumptech.glide.Glide

class DetectionAdapter(private var items: List<DetectionsItem>) : RecyclerView.Adapter<DetectionAdapter.DiseaseViewHolder>() {

    class DiseaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.checked_date)
        val plantName: TextView = itemView.findViewById(R.id.disease_name)
        val confidence: TextView = itemView.findViewById(R.id.confidence)
        val imageView: ImageView = itemView.findViewById(R.id.disease_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.disease_detection_item, parent, false)
        return DiseaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val item = items[position]

        holder.date.text = item.createdAt?.substring(0, 10)
        holder.plantName.text = item.plantName
        holder.confidence.text = item.confidence
        Glide.with(holder.itemView.context)
            .load(item.photoUrl)
            .placeholder(R.drawable.placeholder)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<DetectionsItem>) {
        items = newData
        notifyDataSetChanged()
    }
}
