package com.bangkit.capstone.planitorium.ui.plant_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.data.remote.response.plant.PlantsItem
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListBinding
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetPlantListFragment

class PlantListFragment : Fragment(), BottomSheetPlantListFragment.AddPlantListener{

    private var _binding: FragmentPlantListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: PlantListAdapter
    private lateinit var viewModel: PlantListViewModel
    private lateinit var loadingProgressBar: ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this, PlantViewModelFactory.getInstance(requireContext()))[PlantListViewModel::class.java]
        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingProgressBar = binding.loading

        val recyclerView = binding.plantRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PlantListAdapter(emptyList())
        recyclerView.adapter = adapter
        observePlants()
    }

    private fun adapterOnClickCallback(){
        adapter.setOnItemClickCallback(object: PlantListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlantsItem) {
                val bundle = Bundle()
                bundle.putString("id", data.id)
                findNavController().navigate(R.id.action_navigation_plant_list_to_plantListDetailFragment, bundle, null)
            }
        })
    }

    private fun observePlants(){
        viewModel.getPlantList().observe(viewLifecycleOwner) { result ->
            Log.d("Get Plant List", "New Data Observed")
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        loadingProgressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        loadingProgressBar.visibility = View.GONE
                        val plantList = result.data.plants
                        adapter.updateData(plantList)
                    }
                    is Result.Error -> {
                        loadingProgressBar.visibility = View.GONE
                        Toast.makeText(context, "Cannot Load Plants List, ${result.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        adapterOnClickCallback()
    }

    override fun onPlantAdded() {
        Log.d("On Plant Added", "Added Plant Successful")
        observePlants()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
