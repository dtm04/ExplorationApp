package com.example.explorationapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.explorationapp.databinding.ActivityMainBinding

/**
 * An activity that inflates a layout that has a NavHostFragment.
 * This project tries to stick to MVVM architecture (model-view-viewmodel)
 *  - This means that views handle the UI.
 *  - Views go through viewmodels to access backend data.
 *  - Viewmodels respond back to views using observables (LiveData).
 *  - The Model aspect contains the repositories (single source of truth, talks to DB or web)
 *  - The Model also connects directly to the DB (Room database here, Firebase in the future).
 */
class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var launchIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.main_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up navigation menu
        binding.navigationView.setupWithNavController(navController)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    /*
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.launchApp -> {
                launchApp()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    */

    // Launch arduino control panel, action is bound in xml.  The arduino app must be installed.
    fun launchArduinoApp() {
        //if(menuItem.itemId )
        launchIntent = packageManager.getLaunchIntentForPackage("com.tonydicola.bletest.app")!!
        startActivity(launchIntent)
    }

}
