package com.bangkit.capstone.planitorium.ui.profile

import android.content.Intent
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
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.databinding.ActivityMainBinding
import com.bangkit.capstone.planitorium.databinding.FragmentHomeBinding
import com.bangkit.capstone.planitorium.databinding.FragmentProfileBinding
import com.bangkit.capstone.planitorium.ui.auth.SignUpActivity
import com.bangkit.capstone.planitorium.ui.welcome.WelcomeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

class ProfileFragment : Fragment() {
    private val viewModel: ProfileViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
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
        val imageUrl = ""

        Glide.with(requireContext()).load(imageUrl).apply(RequestOptions.bitmapTransform(CircleCrop())).into(profilePicture)

        logoutButton.setOnClickListener{
            val intent = Intent(requireContext(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
            Toast.makeText(requireContext(), "Successfully Signed Out", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}