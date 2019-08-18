package com.example.explorationapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.explorationapp.adapters.DestinationAdapter
import com.example.explorationapp.databinding.FragmentFavoritesBinding
import com.example.explorationapp.model.DestinationListViewModel
import com.example.explorationapp.utils.InjectorUtils


/**
 * Shows a list of recommended destinations as a mock-up.
 * This fragment can also contains a list of saved user favorites.
 * TODO:
 *  - Allow a user to save a location from map activity.
 *  - Show those here along with recommended destinations.
 *
 */
class FavoritesFragment : Fragment() {
    private val viewModel: DestinationListViewModel by viewModels {
        InjectorUtils.provideDestinationListViewModelFactory(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = DestinationAdapter()
        binding.destinationList.adapter = adapter
        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_zone -> {
                updateData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun subscribeUi(adapter: DestinationAdapter) {
        viewModel.destinations.observe(viewLifecycleOwner) { destns ->
            if (destns != null) adapter.submitList(destns)
        }
    }

    private fun updateData() {
        with(viewModel) {
            if (isFiltered()) {
                clearDestinationLocator()
            } else {
                setDestinationLocator(9)
            }
        }
    }
}
