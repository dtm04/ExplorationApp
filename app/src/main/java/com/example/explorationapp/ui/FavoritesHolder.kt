package com.example.explorationapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explorationapp.R
import com.example.explorationapp.room.Destination

class FavoritesHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
    : RecyclerView.ViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false)) {
    private var favItemView: TextView? = null

    init {
        val favItemView: TextView = itemView.findViewById(R.id.favsTextView)
    }


    fun bind(favDestn: Destination) {
        favItemView?.text = favDestn.lat.toString()
    }
}