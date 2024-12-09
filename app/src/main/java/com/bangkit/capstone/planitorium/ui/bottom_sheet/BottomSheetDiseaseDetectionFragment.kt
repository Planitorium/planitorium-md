package com.bangkit.capstone.planitorium.ui.bottom_sheet

import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.utils.DetectionViewModelFactory
import com.bangkit.capstone.planitorium.core.utils.reduceFileImage
import com.bangkit.capstone.planitorium.core.utils.uriToFile
import com.bangkit.capstone.planitorium.databinding.FragmentBottomSheetDiseaseDetectionBinding
import com.bangkit.capstone.planitorium.ui.detection.DetectionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File

class BottomSheetDiseaseDetectionFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetDiseaseDetectionBinding? = null
    private val binding get() = _binding
    private lateinit var viewModel: DetectionViewModel

    private var selectedImage: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, DetectionViewModelFactory.getInstance(requireContext()))[DetectionViewModel::class.java]
        _binding = FragmentBottomSheetDiseaseDetectionBinding.inflate(inflater, container, false)
        binding?.apply {
            imagePlaceholder.setOnClickListener { imagePickerLauncher.launch(arrayOf("image/*")) }
            analyzeButton.setOnClickListener { performAnalysis() }
            loading.visibility = View.GONE
        }
        return binding?.root
    }

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                selectedImage = uriToFile(it, requireContext()).reduceFileImage()
                binding?.imagePlaceholder?.apply {
                    binding?.imagePlaceholder?.visibility = View.VISIBLE
                    setImageURI(it)
                }
            } ?: Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }

    private fun performAnalysis() {
        binding?.apply {
            val plantName = name.text.toString()

            if (selectedImage == null) {
                Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
                return
            }

            if (plantName.isBlank()) {
                Toast.makeText(requireContext(), "Plant name cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }
            addDetection(selectedImage, plantName, loading)
        }
    }

    private fun addDetection(photo: File?, name: String, loadingProgressBar: ProgressBar){
        viewModel.addDetection(photo, name).observe(viewLifecycleOwner) { result ->
            when(result){
                is Result.Loading -> {
                    loadingProgressBar.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    loadingProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Detection Success", Toast.LENGTH_SHORT).show()
                    dismiss()
                }
                is Result.Error -> {
                    loadingProgressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Detection Failed: ${result.error}", Toast.LENGTH_SHORT).show()
                    Log.d("Detection Error", result.error)
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    interface AddDetectionListener {
        fun onDetectionAdded()
    }

    private var listener: AddDetectionListener? = null

    fun setListener(listener: AddDetectionListener) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        listener?.onDetectionAdded()
    }
}