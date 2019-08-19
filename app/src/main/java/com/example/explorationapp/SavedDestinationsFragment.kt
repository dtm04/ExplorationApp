package com.example.explorationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.explorationapp.adapters.UserDestinationAdapter
import com.example.explorationapp.databinding.FragmentSavedDestinationsBinding
import com.example.explorationapp.model.UserViewModel
import com.example.explorationapp.utils.InjectorUtils


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * Fragment where user can view saved locations, clicking on a location opens up a detailed view
 * and gives the user a share button for that location.
 *
 * A destination must be added to favorites before it appears here.
 */
class SocialFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels {
        InjectorUtils.provideUserViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSavedDestinationsBinding.inflate(inflater, container, false)
        val adapter = UserDestinationAdapter()
        binding.destinationList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: UserDestinationAdapter, binding: FragmentSavedDestinationsBinding) {
        viewModel.userDestinations.observe(viewLifecycleOwner) { destns ->
            binding.hasLocations = !destns.isNullOrEmpty()
        }

        viewModel.userDestinations.observe(viewLifecycleOwner) { result ->
            if (!result.isNullOrEmpty())
                adapter.submitList(result)
        }
    }
}
