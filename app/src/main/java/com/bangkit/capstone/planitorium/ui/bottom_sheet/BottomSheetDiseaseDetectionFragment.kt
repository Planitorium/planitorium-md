package com.bangkit.capstone.planitorium.ui.bottom_sheet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bangkit.capstone.planitorium.databinding.FragmentBottomSheetDiseaseDetectionBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDiseaseDetectionFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDiseaseDetectionBinding? = null
    private val binding get() = _binding

    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDiseaseDetectionBinding.inflate(inflater, container, false)

        binding?.apply {
            imagePlaceholder.setOnClickListener { imagePickerLauncher.launch(arrayOf("image/*")) }

            analyzeButton.setOnClickListener { performAnalysis() }
        }

        return binding?.root
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                binding?.imagePlaceholder?.apply {
                    binding?.imagePlaceholder?.visibility = View.VISIBLE
                    setImageURI(it)
                }
            } ?: Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }

    private fun performAnalysis() {
        binding?.apply {
            val plantName = eventNameField.text.toString()

            if (selectedImageUri == null) {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
            }

            if (plantName.isBlank()) {
                Toast.makeText(requireContext(), "Plant name cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }
        }

        Toast.makeText(requireContext(), "Analyzing image: $selectedImageUri", Toast.LENGTH_SHORT).show()
        this.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}