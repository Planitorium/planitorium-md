package com.bangkit.capstone.planitorium.ui.plant_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListDetailBinding

class PlantListDetailFragment : Fragment() {

    private var _binding: FragmentPlantListDetailBinding? = null
    private val binding get() = _binding!!
    private var date: String? = null
    private var image: String? = null
    private var plantName: String? = null
    private var notes: String? = null
    private var plantedDate: String? = null
    private var harvestDate: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlantListDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            date = bundle.getString("date")
            image = bundle.getString("image")
            plantName = bundle.getString("plant_name")
            notes = bundle.getString("notes")
            plantedDate = bundle.getString("planted_date")
            harvestDate = bundle.getString("harvest_date")
        }
        val dateView: TextView = binding.postDate
        val imageView: ImageView = binding.image
        val nameView: TextView = binding.plantName
        val noteView: TextView = binding.plantNotes
        val plantedDateView: TextView = binding.plantedDate
        val harvestDateView: TextView = binding.harvestDate

        dateView.text = date
        imageView.setImageResource(R.drawable.placeholder)
        nameView.text = plantName
        noteView.text = notes
        plantedDateView.text = plantedDate
        harvestDateView.text = harvestDate
    }
}