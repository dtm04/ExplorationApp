package com.example.explorationapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.explorationapp.R
import com.example.explorationapp.room.UserDestinations

class UserDestinationAdapter :
        ListAdapter<UserDestinations, UserDestinationAdapter.ViewHolder>(DestinationCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.list_item_user_destination, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position).let { destinations ->
            with(holder) {
                itemView.tag = destinations
                bind(createOnClickListener(destinations.destination.destinationId), destinations)
            }
        }
    }

    private fun createOnClickListener(plantId: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = WelcomeFragmentDirections
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemGardenPlantingBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, plantings: PlantAndGardenPlantings) {
            with(binding) {
                clickListener = listener
                viewModel = PlantAndGardenPlantingsViewModel(plantings)
                executePendingBindings()
            }
        }
    }
}

private class DestinationCallback : DiffUtil.ItemCallback<UserDestinations>() {

    override fun areItemsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant.plantId == newItem.plant.plantId
    }

    override fun areContentsTheSame(
        oldItem: PlantAndGardenPlantings,
        newItem: PlantAndGardenPlantings
    ): Boolean {
        return oldItem.plant == newItem.plant
    }
}