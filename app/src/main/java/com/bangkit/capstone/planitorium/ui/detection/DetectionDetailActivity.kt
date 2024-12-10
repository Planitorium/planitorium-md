package com.bangkit.capstone.planitorium.ui.detection

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.utils.DetectionViewModelFactory
import com.bangkit.capstone.planitorium.databinding.ActivityDetectionDetailBinding
import com.bumptech.glide.Glide
import com.bangkit.capstone.planitorium.core.data.Result

class DetectionDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetectionDetailBinding
    private val viewModel: DetectionViewModel by viewModels { DetectionViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detectionId = intent.getStringExtra(EXTRA_DETECTION_ID) ?: return
        setupView()
        observeDetectionDetail(detectionId)
    }

    private fun observeDetectionDetail(detectionId: String) {
        viewModel.getDetectionById(detectionId).observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.loading.visibility = View.VISIBLE
                }
                is Result.Success -> {
                    val detection = result.data.detection

                    binding.apply {
                        loading.visibility = View.GONE

                        Glide.with(this@DetectionDetailActivity)
                            .load(detection?.photo)
                            .placeholder(R.drawable.placeholder)
                            .into(ivDetection)

                        tvResultName.text = detection?.result
                        val formattedConfidence = getString(
                            R.string.confidence,
                            detection?.confidence?.split(".")?.get(0)
                        )
                        tvConfidenceScore.text = formattedConfidence
                        tvCreatedAt.text = detection?.createdAt?.substring(0, 10)
                        tvSuggestionDesc.text = detection?.suggestion
                    }
                }
                is Result.Error -> {
                    binding.loading.visibility = View.GONE
                    Toast.makeText(this, result.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    companion object {
        const val EXTRA_DETECTION_ID = "extra_detection_id"
    }
}