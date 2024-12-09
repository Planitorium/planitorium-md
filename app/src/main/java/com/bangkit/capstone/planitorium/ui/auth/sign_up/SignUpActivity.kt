package com.bangkit.capstone.planitorium.ui.auth.sign_up

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.data.Result
import com.bangkit.capstone.planitorium.core.utils.UserViewModelFactory
import com.bangkit.capstone.planitorium.databinding.ActivitySignUpBinding
import com.bangkit.capstone.planitorium.ui.auth.sign_in.SignInActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val viewModel by viewModels<SignUpViewModel> { UserViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()
        observeSignUpResult()

        binding.signInTextView.setOnClickListener{
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            this.finish()
        }
    }

    private fun setupAction() {
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (email.isEmpty()) {
                binding.emailTextInputLayout.error = R.string.email_empty.toString()
                return@setOnClickListener
            }

            if (username.isEmpty()) {
                binding.usernameTextInputLayout.error = R.string.username_empty.toString()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.passwordTextInputLayout.error = R.string.password_empty.toString()
                return@setOnClickListener
            }

            viewModel.signUp(email, username, password)
        }
    }

    private fun observeSignUpResult() {
        viewModel.signUpResult.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle(R.string.signup_success)
                        setMessage(R.string.signup_message)
                        setPositiveButton(R.string.signup_next) { _, _ ->
                            finish()
                        }
                        create()
                        show()
                    }
                }
                is Result.Error -> {
                    showLoading(false)
                    showToast(result.error)
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

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}