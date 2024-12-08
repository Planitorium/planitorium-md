package com.bangkit.capstone.planitorium.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.databinding.FragmentHomeBinding
import com.bangkit.capstone.planitorium.ui.plant_list.PlantListViewModel
import com.bangkit.capstone.planitorium.ui.plant_list.PlantViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var viewModel: PlantListViewModel

    private val binding get() = _binding!!
    private lateinit var adapter: HomePlantAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, PlantViewModelFactory.getInstance(requireContext()))[PlantListViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("DefaultLocale")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendarView: CalendarView = binding.calendarView
        val noDataTextView: TextView = binding.noDataTextView
        var selectedDate = ""
        val recyclerView: RecyclerView = binding.eventsRecyclerView
        adapter = HomePlantAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val formattedMonth = String.format("%02d", month + 1) // Add 1 to month as it starts from 0
            val formattedDay = String.format("%02d", dayOfMonth)  // Ensure day is always 2 digits
            selectedDate = "$year-$formattedMonth-$formattedDay"
            Log.d("CalendarView", "Selected Date: $selectedDate")

            viewModel.getPlantListByDate(selectedDate).observe(viewLifecycleOwner){ result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                        }
                        is Result.Success -> {
                            val plantList = result.data.plants
                            if (plantList.isEmpty()) {
                                recyclerView.visibility = View.GONE
                                noDataTextView.visibility = View.VISIBLE
                            } else {
                                recyclerView.visibility = View.VISIBLE
                                noDataTextView.visibility = View.GONE

                                adapter = HomePlantAdapter(plantList)
                                recyclerView.adapter = adapter
                            }
                        }
                        is Result.Error -> {
                            Toast.makeText(context, "Cannot Load Plants List, ${result.error}", Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}