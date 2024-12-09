package com.bangkit.capstone.planitorium.ui.detection

import androidx.recyclerview.widget.ListAdapter
import com.bangkit.capstone.planitorium.core.data.remote.response.detection.DetectionsItem
import com.bangkit.capstone.planitorium.databinding.DiseaseDetectionItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.R
import com.bumptech.glide.Glide

class DetectionAdapter : ListAdapter<DetectionsItem, DetectionAdapter.DetectionViewHolder>(DiffCallback) {

    private var onItemClickListener: ((DetectionsItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionViewHolder {
        val binding = DiseaseDetectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetectionViewHolder, position: Int) {
        val detection = getItem(position)
        holder.bind(detection)
    }

    inner class DetectionViewHolder(private val binding: DiseaseDetectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(detection: DetectionsItem) {
            binding.apply {
                checkedDate.text = detection.createdAt?.substring(0, 10)
                diseaseName.text = detection.plantName
                result.text = detection.result

                val formattedConfidence = itemView.context.getString(
                    R.string.confidence,
                    detection.confidence?.split(".")?.get(0)
                )
                confidence.text = formattedConfidence

                Glide.with(itemView.context)
                    .load(detection.photoUrl)
                    .into(diseaseImage)

                root.setOnClickListener {
                    onItemClickListener?.let { clickListener ->
                        clickListener(detection)
                    }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (DetectionsItem) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DetectionsItem>() {
            override fun areItemsTheSame(oldItem: DetectionsItem, newItem: DetectionsItem): Boolean {
                return oldItem.createdAt == newItem.createdAt
            }

            override fun areContentsTheSame(oldItem: DetectionsItem, newItem: DetectionsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
