package com.bangkit.capstone.planitorium.core.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.planitorium.core.data.repository.UserRepository
import com.bangkit.capstone.planitorium.core.di.Injection
import com.bangkit.capstone.planitorium.ui.auth.sign_in.SignInViewModel
import com.bangkit.capstone.planitorium.ui.auth.sign_up.SignUpViewModel
import com.bangkit.capstone.planitorium.ui.main.MainViewModel
import com.bangkit.capstone.planitorium.ui.profile.ProfileViewModel

class UserViewModelFactory private constructor(
    private val userRepository: UserRepository,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                SignInViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                SignUpViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: UserViewModelFactory? = null

        fun getInstance(context: Context): UserViewModelFactory {
            return instance ?: synchronized(this) {
                val userRepository = Injection.provideUserRepository(context)
                instance ?: UserViewModelFactory(userRepository)
            }.also { instance = it }
        }
    }
}