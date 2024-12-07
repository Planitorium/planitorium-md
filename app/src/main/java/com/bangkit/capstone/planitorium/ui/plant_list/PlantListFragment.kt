package com.bangkit.capstone.planitorium.ui.plant_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantsItem
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.Time
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PlantListFragment : Fragment() {

    private var _binding: FragmentPlantListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantListAdapter
    private lateinit var viewModel: PlantListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, PlantViewModelFactory.getInstance(requireContext()))[PlantListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loadingProgressBar = binding.loading

        val recyclerView = binding.plantRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PlantListAdapter(emptyList())
        recyclerView.adapter = adapter

        viewModel.getPlantList().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        loadingProgressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        loadingProgressBar.visibility = View.GONE
                        val plantList = result.data.plants
                        adapter = PlantListAdapter(plantList)
                        adapterOnClickCallback()
                        recyclerView.adapter = adapter
                    }
                    is Result.Error -> {
                        loadingProgressBar.visibility = View.GONE
                        Toast.makeText(context, "Cannot Load Plants List, ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun adapterOnClickCallback(){
        adapter.setOnItemClickCallback(object: PlantListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlantsItem) {
                val bundle = Bundle()
                bundle.putString("id", data.id)
                bundle.putString("image", data.photo)
                bundle.putString("date",data.startTime.toString())
                bundle.putString("plant_name", data.name)
                bundle.putString("notes", data.description)
                bundle.putString("planted_date",data.startTime.toString())
                bundle.putString("harvest_date", data.endTime.toString())
                findNavController().navigate(R.id.action_navigation_plant_list_to_plantListDetailFragment, bundle, null)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
