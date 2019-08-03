package com.example.explorationapp.ui

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.explorationapp.R

class FavoritesAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<FavoritesAdapter.WordViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var favorites = emptyList<Location>() // Cached copy of words

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favItemView: TextView = itemView.findViewById(R.id.favsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val favoriteView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(favoriteView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = favorites[position]
        holder.favItemView.text = current.latitude.toString()
    }

    internal fun setFavorites(favs: List<Location>) {
        this.favorites = favs
        notifyDataSetChanged()
    }

    override fun getItemCount() = favorites.size
}