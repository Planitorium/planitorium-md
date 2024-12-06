package com.bangkit.capstone.planitorium.ui.profile

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
import com.bangkit.capstone.planitorium.databinding.FragmentProfileBinding
import com.bumptech.glide.Glide
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.utils.ViewModelFactory
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels { ViewModelFactory.getInstance(requireContext()) }
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
                    profile?.let {
                        email.text = it.email
                        Glide.with(requireContext())
                            .load(it.photo)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
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

        return root
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}