package com.example.explorationapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.explorationapp.FavoritesFragmentDirections
import com.example.explorationapp.databinding.ListItemDestinationBinding
import com.example.explorationapp.room.Destination

class DestinationAdapter : ListAdapter<Destination, DestinationAdapter.ViewHolder>(DestinationDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val destn = getItem(position)
        holder.apply {
            bind(createOnClickListener(destn.destinationId), destn)
            itemView.tag = destn
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemDestinationBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(destnId: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = FavoritesFragmentDirections.actionFavoritesFragmentToStatsFragment(destnId)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemDestinationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: Destination) {
            binding.apply {
                clickListener = listener
                destination = item
                executePendingBindings()
            }
        }
    }
}

private class DestinationDiffCallback : DiffUtil.ItemCallback<Destination>() {

    override fun areItemsTheSame(oldItem: Destination, newItem: Destination): Boolean {
        return oldItem.destinationId == newItem.destinationId
    }

    override fun areContentsTheSame(oldItem: Destination, newItem: Destination): Boolean {
        return oldItem == newItem
    }
}