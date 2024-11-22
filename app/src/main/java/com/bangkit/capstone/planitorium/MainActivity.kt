package com.bangkit.capstone.planitorium

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bangkit.capstone.planitorium.databinding.ActivityMainBinding
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetDiseaseDetectionFragment
import com.bangkit.capstone.planitorium.ui.bottom_sheet.BottomSheetPlantListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_plant_list, R.id.navigation_detection, R.id.navigation_profile
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
                }
                R.id.navigation_detection -> {
                    binding.fab.visibility = View.VISIBLE
                    binding.fab.setOnClickListener {
                        BottomSheetDiseaseDetectionFragment().show(supportFragmentManager, "disease_detection_bottom_sheet")
                    }
                }
                else -> {
                    binding.fab.visibility = View.GONE
                }
            }
        }
    }
}