package com.bangkit.capstone.planitorium.ui.disease_detection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.databinding.FragmentDiseaseDetectionBinding
import com.bangkit.capstone.planitorium.model.DiseaseItem

class DiseaseDetectionFragment : Fragment() {

    private var _binding: FragmentDiseaseDetectionBinding? = null
    private val binding get() = _binding!!

    private lateinit var diseaseDetectionAdapter: DiseaseDetectionAdapter
    private val diseaseList = listOf(
        DiseaseItem("","2024-11-21", "Tomato", 80),
        DiseaseItem("","2024-11-22", "Potato", 30),
        DiseaseItem("","2024-11-23", "Carrot", 50)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiseaseDetectionBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Set up RecyclerView
        val recyclerView: RecyclerView = binding.recyclerView
        diseaseDetectionAdapter = DiseaseDetectionAdapter(diseaseList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = diseaseDetectionAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}