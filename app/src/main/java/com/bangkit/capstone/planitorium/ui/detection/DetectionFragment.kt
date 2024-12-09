package com.bangkit.capstone.planitorium.ui.detection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bangkit.capstone.planitorium.core.data.Result
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.capstone.planitorium.core.utils.DetectionViewModelFactory
import com.bangkit.capstone.planitorium.databinding.FragmentDiseaseDetectionBinding
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetDiseaseDetectionFragment

class DetectionFragment: Fragment(), BottomSheetDiseaseDetectionFragment.AddDetectionListener{

    private var _binding: FragmentDiseaseDetectionBinding? = null
    private val binding get() = _binding

    private val viewModel: DetectionViewModel by viewModels { DetectionViewModelFactory.getInstance(requireContext()) }
    private lateinit var adapter: DetectionAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiseaseDetectionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeDetection()

        viewModel.getDetectionList()
    }

    private fun setupRecyclerView() {
        adapter = DetectionAdapter().apply {
            setOnItemClickListener { detection ->
                val intent = Intent(requireContext(), DetectionDetailActivity::class.java)
                intent.putExtra("DETECTION_ID", detection.plantName)
                startActivity(intent)
            }
        }

        binding?.recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerView?.adapter = adapter
    }

    private fun observeDetection() {
        viewModel.detectionListResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    adapter.submitList(result.data.data?.detections)
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(context, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDetectionAdded() {
        viewModel.getDetectionList()
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.loading?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}