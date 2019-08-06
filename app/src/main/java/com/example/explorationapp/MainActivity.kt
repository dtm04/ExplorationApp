/**************************
 *  Exploration App: WIT-Boston with SUSTECH-Shenzhen
 *
 *  An app for location sharing and discovery.  Earn reputation as you
 *  travel.  Share notable locations with friends.
 *
 *  Integrated with Arduino wearable technology, GPS services, Google maps.
 *  Built using Kotlin in Android Studio
 *  Architecture and Code inspired by Google - Sunflower.
 *************************/
package com.example.explorationapp

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
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
 * TODO list
 *  -- fab button on map link to save location + add photo option
 *  -- Misty/Foggy map overlay
 *  -- Connect bluetooth receiver to Arduino
 *  -- Configure Arduino drone to use GPS and/or camera
 *  -- Upload arduino statstics to application
 *  -- Rename fragments to make more sense
 *  -- Convert tile overlay to data class, store traveled areas using ROOM
 *  -- (Optional) Convert to cloud based storage
 *  -- (Optional) Add user Authentication and accounts
 *  -- (Optional) Integrate with google places API find nearby areas
 */
/**
 * An activity that inflates a layout that has a NavHostFragment.
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

    fun launchApp(menuItem: MenuItem) {
        //if(menuItem.itemId )
        launchIntent = packageManager.getLaunchIntentForPackage("com.tonydicola.bletest.app")!!
        startActivity(launchIntent)
    }
}
