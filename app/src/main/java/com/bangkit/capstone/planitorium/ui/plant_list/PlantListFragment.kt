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
import com.bangkit.capstone.planitorium.data.remote.response.PlantsItem
import com.bangkit.capstone.planitorium.data.remote.response.StartTime
import com.bangkit.capstone.planitorium.databinding.FragmentPlantListBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
            ViewModelProvider(this, PlantViewModelFactory.getInstance(requireContext()))[PlantListViewModel::class.java]

        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.plantRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = PlantListAdapter(emptyList())
        recyclerView.adapter = adapter


//        val dummyData = listOf(
//            PlantsItem("Rose","Do not forget to water daily", "2024-11-22", "2024-12-22"),
//            PlantsItem("Tulip","Do not forget to water daily", "2024-11-22", "2024-12-22"),
//            PlantsItem("Cactus","Do not forget to water daily", "2024-11-22", "2024-12-22"),
//        )

        plantListViewModel.plantList.observe(viewLifecycleOwner) { item ->
            if (item != null){
                adapter = PlantListAdapter(item)
                recyclerView.adapter = adapter
            }
            adapterOnClickCallback()
        }
        return root
    }

    private fun adapterOnClickCallback(){
        adapter.setOnItemClickCallback(object: PlantListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlantsItem) {
                val bundle = Bundle()
                bundle.putString("image", data.photo)
                bundle.putString("date", convertTimestampToDate(data.startTime))
                bundle.putString("plant_name", data.name)
                bundle.putString("notes", data.description)
                bundle.putString("planted_date", convertTimestampToDate(data.startTime))
                bundle.putString("harvest_date", convertTimestampToDate(StartTime(nanoseconds = data.endTime?.nanoseconds, seconds = data.endTime?.seconds)))
                findNavController().navigate(R.id.action_navigation_plant_list_to_plantListDetailFragment, bundle, null)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun convertTimestampToDate(time: StartTime?): String? {
        // Ensure the time object and seconds are not null
        if (time?.seconds == null) return null

        // Convert seconds to milliseconds
        val secondsInMillis = time.seconds * 1000L

        // Convert nanoseconds to milliseconds (if available)
        val nanosecondsInMillis = (time.nanoseconds ?: 0) / 1_000_000L

        // Combine both to get total milliseconds
        val totalMillis = secondsInMillis + nanosecondsInMillis

        // Convert to Date and format
        val date = Date(totalMillis)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(date)
    }
}
