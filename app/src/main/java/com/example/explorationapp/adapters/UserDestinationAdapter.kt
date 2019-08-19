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
import com.example.explorationapp.SocialFragmentDirections
import com.example.explorationapp.WelcomeFragmentDirections
import com.example.explorationapp.databinding.ListItemUserDestinationBinding
import com.example.explorationapp.model.UserDestinationViewModel
import com.example.explorationapp.room.UserDestinations

/**
 * Adapter used for Recycler View.
 * Used when viewing list of saved locations.  Click destination --> detail view
 */
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

    private fun createOnClickListener(destinationId: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = SocialFragmentDirections.actionSocialFragmentToStatsFragment(destinationId)
            it.findNavController().navigate(direction)
        }
    }

    class ViewHolder(
        private val binding: ListItemUserDestinationBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, destinations: UserDestinations) {
            with(binding) {
                clickListener = listener
                viewModel = UserDestinationViewModel(destinations)
                executePendingBindings()
            }
        }
    }
}

private class DestinationCallback : DiffUtil.ItemCallback<UserDestinations>() {

    override fun areItemsTheSame(
        oldItem: UserDestinations,
        newItem: UserDestinations
    ): Boolean {
        return oldItem.destination.destinationId == newItem.destination.destinationId
    }

    override fun areContentsTheSame(
        oldItem: UserDestinations,
        newItem: UserDestinations
    ): Boolean {
        return oldItem.destination == newItem.destination
    }
}