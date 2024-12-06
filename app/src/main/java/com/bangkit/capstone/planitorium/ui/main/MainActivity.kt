package com.bangkit.capstone.planitorium.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.capstone.planitorium.R
import com.bangkit.capstone.planitorium.core.utils.ViewModelFactory
import com.bangkit.capstone.planitorium.databinding.ActivityMainBinding
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetDiseaseDetectionFragment
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetPlantListFragment
import com.bangkit.capstone.planitorium.ui.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeSession()
    }

    private fun observeSession() {
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                if (user.token.isNotEmpty()) {
                    setupView()
                }
            }
        }
    }

    private fun setupView() {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_plant_list,
                R.id.navigation_detection,
                R.id.navigation_profile
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, navigation, _ ->
            when(navigation.id) {
                R.id.navigation_plant_list -> {
                    binding.fab.visibility = View.VISIBLE
                    binding.fab.setOnClickListener {
                        BottomSheetPlantListFragment().show(supportFragmentManager, "plant_list_bottom_sheet")
                    }
                    navView.visibility = View.VISIBLE
                }
                R.id.navigation_detection -> {
                    binding.fab.visibility = View.VISIBLE
                    binding.fab.setOnClickListener {
                        BottomSheetDiseaseDetectionFragment().show(supportFragmentManager, "disease_detection_bottom_sheet")
                    }
                    navView.visibility = View.VISIBLE
                }
                R.id.navigation_plant_list_detail -> {
                    binding.fab.visibility = View.GONE
                    navView.visibility = View.GONE
                }
                else -> {
                    binding.fab.visibility = View.GONE
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }
}