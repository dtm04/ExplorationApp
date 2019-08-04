package com.example.explorationapp.ui


import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.explorationapp.R
import com.example.explorationapp.room.Destination


class DestinationListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<DestinationListAdapter.FavsHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var destinationLiveData = emptyList<Destination>() // Cached copy of words

    inner class FavsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val favoritesView: TextView = itemView.findViewById(R.id.favsTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return FavsHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavsHolder, position: Int) {
        val current = destinationLiveData[position]
        holder.favoritesView.text = current.timestamp.toString()
    }

    internal fun setFavorites(favs: List<Destination>) {
        this.destinationLiveData = favs
        notifyDataSetChanged()
    }

    override fun getItemCount() = destinationLiveData.size
}


