package com.bangkit.capstone.planitorium.ui.plant_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListBinding
import com.bangkit.capstone.planitorium.model.PlantItem

class PlantListFragment : Fragment() {

    private var _binding: FragmentPlantListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val plantListViewModel =
            ViewModelProvider(this)[PlantListViewModel::class.java]

        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.plantRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dummyData = listOf(
            PlantItem("url","2024-11-22", "Rose", "Do not forget to water daily"),
            PlantItem("url","2024-11-21", "Tulip", "Always keep in sunlight"),
            PlantItem("url","2024-11-20", "Cactus", "Do not forget to water daily")
        )

        recyclerView.adapter = PlantListAdapter(dummyData)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
