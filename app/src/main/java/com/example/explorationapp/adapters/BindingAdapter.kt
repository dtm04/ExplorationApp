package com.example.explorationapp.adapters


import android.view.View
import androidx.databinding.BindingAdapter

// Source: Google Sunflower Project
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}
