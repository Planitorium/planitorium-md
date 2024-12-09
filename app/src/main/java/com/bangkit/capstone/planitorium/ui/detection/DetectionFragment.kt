package com.bangkit.capstone.planitorium.ui.detection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.databinding.FragmentDiseaseDetectionBinding
import com.bangkit.capstone.planitorium.core.data.model.DiseaseItem
import com.bangkit.capstone.planitorium.core.utils.DetectionViewModelFactory
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetDiseaseDetectionFragment

class DetectionFragment : Fragment(), BottomSheetDiseaseDetectionFragment.AddDetectionListener {

    private var _binding: FragmentDiseaseDetectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetectionViewModel
    private lateinit var adapter: DetectionAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, DetectionViewModelFactory.getInstance(requireContext()))[DetectionViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiseaseDetectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        loadingProgressBar = binding.loading
        adapter = DetectionAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeDetection()
    }

    private fun observeDetection(){
        viewModel.getDetectionList().observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    val detectionList = result.data.data?.detections
                    if (detectionList != null) {
                        adapter.updateData(detectionList)
                    }
                }
                is Result.Error -> {
                    loadingProgressBar.visibility = View.GONE
                    Toast.makeText(context, "Cannot Load Detection List, ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDetectionAdded() {
        Log.d("Listening Add Detection", "Added Detection Successful")
        observeDetection()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}