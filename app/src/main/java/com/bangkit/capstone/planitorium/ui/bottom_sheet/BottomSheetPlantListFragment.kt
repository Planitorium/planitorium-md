package com.bangkit.capstone.planitorium.ui.bottom_sheet

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.databinding.FragmentBottomSheetPlantListBinding
import com.bangkit.capstone.planitorium.ui.plant_list.PlantListViewModel
import com.bangkit.capstone.planitorium.ui.plant_list.PlantViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.bangkit.capstone.planitorium.core.data.Result

class BottomSheetPlantListFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetPlantListBinding? = null
    private val binding get() = _binding

    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val plantListViewModel =
            ViewModelProvider(this, PlantViewModelFactory.getInstance(requireContext()))[PlantListViewModel::class.java]
        _binding = FragmentBottomSheetPlantListBinding.inflate(inflater, container, false)

        binding?.apply {
            startTimeField.setOnClickListener { showDatePicker(startTimeField) }

            endTimeField.setOnClickListener { showDatePicker(endTimeField) }

            imagePlaceholder.setOnClickListener { imagePickerLauncher.launch(arrayOf("image/*")) }

            addPlantButton.setOnClickListener{ validateAndSubmit(plantListViewModel) }
            loading.visibility = View.GONE

        }

        return binding?.root
    }


    private fun showDatePicker(targetField: TextInputEditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                targetField.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
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

    private fun validateAndSubmit(viewModel: PlantListViewModel) {
        binding?.apply {
            try{
                val plantName = eventNameField.text.toString()
                val notes = noteField.text.toString()
                val startTime = startTimeField.text.toString()
                val endTime = endTimeField.text.toString()
                val photoFile: File?


                if (plantName.isBlank()) {
                    Toast.makeText(requireContext(), "Plant name cannot be empty", Toast.LENGTH_SHORT).show()
                    return
                }

                if (startTime.isBlank() || endTime.isBlank()) {
                    Toast.makeText(requireContext(), "Start and end dates must be selected", Toast.LENGTH_SHORT).show()
                    return
                }

                if (selectedImageUri == null) {
                    Toast.makeText(requireContext(), "Please select an image first", Toast.LENGTH_SHORT).show()
                    return
                }else{
                    photoFile = requireContext().createFileFromUri(selectedImageUri)
                    Log.d("file name", photoFile.name)
                }

                viewModel.addPlant(photoFile, plantName, notes, startTime, endTime)

                viewModel.plantAddStatus.observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
                            binding?.loading?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.loading?.visibility = View.GONE
                            Toast.makeText(requireContext(), "Plant Added Successfully", Toast.LENGTH_SHORT).show()
                            dismiss()
                        }
                        is Result.Error -> {
                            binding?.loading?.visibility = View.GONE
                            Toast.makeText(requireContext(), "Error Adding Plant: ${result.error}", Toast.LENGTH_SHORT).show()
                            Log.d("add plant Error", result.error)
                        }
                    }
                }
                Toast.makeText(requireContext(), "Plant Added Successfully", Toast.LENGTH_SHORT).show()
                dismiss()
            }catch (e: Error){
                Toast.makeText(requireContext(), "Error Adding Plant: $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Context.createFileFromUri(uri: Uri?): File {
        val contentResolver = this.contentResolver
        val inputStream = uri?.let { contentResolver.openInputStream(it) } ?: throw IllegalArgumentException("Cannot open input stream from URI")
        val tempFile = File.createTempFile("temp_image", ".jpg", cacheDir)

        tempFile.outputStream().use { outputStream ->
            inputStream.copyTo(outputStream)
        }

        return tempFile
    }
}