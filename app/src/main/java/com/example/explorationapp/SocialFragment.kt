package com.example.explorationapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.explorationapp.adapters.UserDestinationAdapter
import com.example.explorationapp.databinding.FragmentSocialBinding
import com.example.explorationapp.model.UserDestinationViewModel
import com.example.explorationapp.model.UserViewModel
import com.example.explorationapp.model.UserViewModelFactory
import com.example.explorationapp.utils.InjectorUtils


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SocialFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SocialFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
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
        val binding = FragmentSocialBinding.inflate(inflater, container, false)
        val adapter = UserDestinationAdapter()
        binding.destinationList.adapter = adapter
        subscribeUi(adapter, binding)
        return binding.root
    }

    private fun subscribeUi(adapter: UserDestinationAdapter, binding: FragmentSocialBinding) {
        viewModel.userDestinations.observe(viewLifecycleOwner) { destns ->
            binding.hasLocations = !destns.isNullOrEmpty()
        }

        viewModel.userDestinations.observe(viewLifecycleOwner) { result ->
            if (!result.isNullOrEmpty())
                adapter.submitList(result)
        }
    }
}
