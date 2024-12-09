package com.bangkit.capstone.planitorium.ui.plant_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.utils.PlantViewModelFactory
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListDetailBinding
import com.bumptech.glide.Glide

class PlantListDetailFragment : Fragment() {

    private var _binding: FragmentPlantListDetailBinding? = null
    private lateinit var viewModel: PlantListViewModel
    private val binding get() = _binding!!
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =  ViewModelProvider(this, PlantViewModelFactory.getInstance(requireContext()))[PlantListViewModel::class.java]
    }

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
            id = bundle.getString("id")
        }
        val imageView: ImageView = binding.image
        val nameView: TextView = binding.plantName
        val noteView: TextView = binding.plantNotes
        val plantedDateView: TextView = binding.plantedDate
        val harvestDateView: TextView = binding.harvestDate
        val loadingProgressBar: ProgressBar = binding.loading


        viewModel.getPlantDetail(id.toString()).observe(viewLifecycleOwner){ result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        loadingProgressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        loadingProgressBar.visibility = View.GONE
                        val plant = result.data.plant

                        // binding data
                        Glide.with(this)
                            .load(plant.photo)
                            .placeholder(R.drawable.placeholder)
                            .into(imageView)

                        nameView.text = plant.name
                        noteView.text = plant.description
                        plantedDateView.text = plant.startTime.toString()
                        harvestDateView.text = plant.endTime.toString()
                    }
                    is Result.Error -> {
                        loadingProgressBar.visibility = View.GONE
                        Toast.makeText(context, "Cannot Load Plants Detail, ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}