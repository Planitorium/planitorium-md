package com.bangkit.capstone.planitorium.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.databinding.FragmentHomeBinding
import com.bangkit.capstone.planitorium.core.data.model.PlantItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var homePlantAdapter: HomePlantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dummyData = listOf(
            PlantItem("url","2024-11-22", "Rose", "Do not forget to water daily", "2024-11-22", "2024-12-22"),
            PlantItem("url","2024-11-21", "Tulip", "Always keep in sunlight", "2024-11-22", "2024-12-22"),
            PlantItem("url","2024-11-20", "Cactus", "Do not forget to water daily", "2024-11-22", "2024-12-22")
        )

        val recyclerView: RecyclerView = binding.eventsRecyclerView
        homePlantAdapter = HomePlantAdapter(dummyData)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = homePlantAdapter


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}