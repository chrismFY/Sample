package com.joker.ui.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.joker.BASE_URL
import com.joker.R
import com.joker.databinding.FragmentMainBinding
import com.joker.viewModel.preView.SharedPreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main.*

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class PreviewFragment : Fragment() {
    private lateinit var binding:FragmentMainBinding
    private val sharedPreViewModel: SharedPreViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main,container,false)
        binding.apply {
            mainFragmentViewModel = sharedPreViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        retainInstance = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
    }

    private fun initClick() {
        add.setOnClickListener {
            activity?.let { it1 -> sharedPreViewModel.getJokeFromServer(it1) }

        }
        favorite.setOnClickListener {
            context?.let { it1 -> sharedPreViewModel.addToFavorite(it1) }
        }

        share.setOnClickListener {
            val content = ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(BASE_URL))
                .setQuote(sharedPreViewModel.jokeInfo?.value?.joke)
                .build()
            val shareDialog = ShareDialog(this)
            shareDialog.show(content, ShareDialog.Mode.AUTOMATIC)
        }
    }

}