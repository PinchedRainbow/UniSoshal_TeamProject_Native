package com.faheemsaleem.unisoshal

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.faheemsaleem.unisoshal.databinding.ActivityBottomNavBarBinding
import com.google.android.material.appbar.MaterialToolbar

class BottomNavBarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBottomNavBarBinding
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = binding.toolBar

        // set subtitle based on the fragment
        toolbar.subtitle = "Testing"
        // change the navigationBarColor to match the colorPrimaryDark
        window.navigationBarColor = resources.getColor(R.color.NotificationColor, theme)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_nav_bar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // set subtitle based on the fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolbar.subtitle = destination.label
        }

    }
}
