package com.example.explorationapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.explorationapp.model.UserViewModel

/**
 * An activity that inflates a layout that has a NavHostFragment.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
