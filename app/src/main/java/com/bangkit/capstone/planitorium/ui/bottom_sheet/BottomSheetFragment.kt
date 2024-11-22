package com.bangkit.capstone.planitorium.ui.bottom_sheet

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import com.bangkit.capstone.planitorium.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var selectedImageUri: Uri? = null

    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
            uri?.let {
                selectedImageUri = it
                view?.findViewById<ImageView>(R.id.imagePlaceholder)?.apply {
                    visibility = View.VISIBLE
                    setImageURI(it)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        val plantNameField: EditText = view.findViewById(R.id.eventNameField)
        val noteField: EditText = view.findViewById(R.id.noteField)
        val startTimeField: EditText = view.findViewById(R.id.startTimeField)
        val endTimeField: EditText = view.findViewById(R.id.endTimeField)
        val imagePreview: ImageView = view.findViewById(R.id.imagePlaceholder)
        val addPlantButton: Button = view.findViewById(R.id.addPlantButton)

        startTimeField.setOnClickListener { showDatePicker(startTimeField) }
        endTimeField.setOnClickListener { showDatePicker(endTimeField) }

        imagePreview.setOnClickListener {
            imagePickerLauncher.launch(arrayOf("image/*"))
        }

        addPlantButton.setOnClickListener{
            this.dismiss()
        }

        return view
    }

    private fun showDatePicker(targetField: EditText) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val date = "$dayOfMonth/${month + 1}/$year"
                targetField.setText(date)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}