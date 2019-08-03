package com.example.explorationapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.explorationapp.room.Destination

class ListAdapter(private val list: List<Destination>)
    : RecyclerView.Adapter<FavoritesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FavoritesHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FavoritesHolder, position: Int) {
        val destn: Destination = list[position]
        holder.bind(destn)
    }

    override fun getItemCount(): Int = list.size

}