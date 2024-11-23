package com.bangkit.capstone.planitorium.ui.plant_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListBinding
import com.bangkit.capstone.planitorium.model.PlantItem

class PlantListFragment : Fragment() {

    private var _binding: FragmentPlantListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantListAdapter

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
            PlantItem("url","2024-11-22", "Rose", "Do not forget to water daily", "2024-11-22", "2024-12-22"),
            PlantItem("url","2024-11-21", "Tulip", "Always keep in sunlight", "2024-11-22", "2024-12-22"),
            PlantItem("url","2024-11-20", "Cactus", "Do not forget to water daily", "2024-11-22", "2024-12-22")
        )

        adapter = PlantListAdapter(dummyData)
        recyclerView.adapter = adapter
        adapterOnClickCallback()
        return root
    }

    private fun adapterOnClickCallback(){
        adapter.setOnItemClickCallback(object: PlantListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlantItem) {
                val bundle = Bundle()
                bundle.putString("image", data.image)
                bundle.putString("date", data.date)
                bundle.putString("plant_name", data.plantName)
                bundle.putString("notes", data.notes)
                bundle.putString("planted_date", data.plantedDate)
                bundle.putString("harvest_date", data.harvestDate)
                findNavController().navigate(R.id.action_navigation_plant_list_to_plantListDetailFragment, bundle, null)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
