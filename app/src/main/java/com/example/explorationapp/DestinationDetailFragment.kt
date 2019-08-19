package com.example.explorationapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.explorationapp.databinding.FragmentDetailsBinding
import com.example.explorationapp.model.DestinationDetailViewModel
import com.example.explorationapp.utils.InjectorUtils
import com.google.android.material.snackbar.Snackbar

/**
 * Provides the detailed view of a destination.
 * User can view destination photo and description here.
 */
class DestinationDetailFragment : Fragment() {

    private val args: DestinationDetailFragmentArgs by navArgs()
    private lateinit var shareText: String
    private val destinationDetailViewModel: DestinationDetailViewModel by viewModels{
        InjectorUtils.provideDestinationDetailViewModelFactory(requireActivity(), args.destinationId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailsBinding>(
            inflater, R.layout.fragment_details, container, false).apply {
            viewModel = destinationDetailViewModel
            lifecycleOwner = this@DestinationDetailFragment
            fab.setOnClickListener { view ->
                destinationDetailViewModel.addDestination()
                Snackbar.make(view, R.string.destination_saved, Snackbar.LENGTH_LONG).show()
            }
        }

        destinationDetailViewModel.destination.observe(this) { destn ->
            shareText = getString(R.string.share_text_fav, destn.name)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Suppress("DEPRECATION")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                val shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setText(shareText)
                    .setType("text/plain")
                    .createChooserIntent()
                    .apply {
                        // https://android-developers.googleblog.com/2012/02/share-with-intents.html
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            // If we're on Lollipop, we can open the intent as a document
                            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                        } else {
                            // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                        }
                    }
                startActivity(shareIntent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
