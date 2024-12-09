package com.bangkit.capstone.planitorium.ui.profile

import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.utils.UserViewModelFactory
import com.bangkit.capstone.planitorium.core.utils.reduceFileImage
import com.bangkit.capstone.planitorium.core.utils.uriToFile

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels { UserViewModelFactory.getInstance(requireContext()) }
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProfile()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val profilePicture: ImageView = binding.profilePicture
        val email: TextView = binding.email
        val logoutButton: Button = binding.logout

        viewModel.profileResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }

                is Result.Success -> {
                    showLoading(false)
                    val profile = result.data.profile
                    profile?.let { user ->
                        email.text = user.email
                        Glide.with(requireContext())
                            .load(user.photo)
                            .circleCrop()
                            .placeholder(R.drawable.placeholder)
                            .into(profilePicture)
                    }
                }

                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }

        logoutButton.setOnClickListener {
            viewModel.logout()
            Toast.makeText(requireContext(), "Successfully Signed Out", Toast.LENGTH_SHORT).show()
        }

        profilePicture.setOnClickListener { startGallery() }

        return root
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            val photo = uriToFile(uri, requireContext()).reduceFileImage()

            viewModel.uploadPhoto(photo).observe(requireActivity()) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            showLoading(true)
                        }

                        is Result.Success -> {
                            Toast.makeText(requireContext(), result.data.message, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                            binding.profilePicture.setImageURI(uri)
                        }

                        is Result.Error -> {
                            Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}