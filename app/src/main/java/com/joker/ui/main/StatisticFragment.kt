package com.joker.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.joker.BR
import com.joker.R
import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Words
import com.joker.databinding.FavoriteFragmentBinding
import com.joker.databinding.ItemJokeListBinding
import com.joker.utils.dataBinding.BaseBindingHolder
import com.joker.utils.dataBinding.adapter.BaseBindingRecycleAdapter
import com.joker.viewModel.preView.SharedPreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.favorite_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.withContext

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class StatisticFragment : Fragment() {
    private lateinit var binding: FavoriteFragmentBinding
    private val sharedPreViewModel: SharedPreViewModel by activityViewModels()
    lateinit var adapter: BaseBindingRecycleAdapter<Words>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            jokeListViewModel = sharedPreViewModel
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter = BaseBindingRecycleAdapter(BR.jokeInfoWord, R.layout.words_list,
            object : DiffUtil.ItemCallback<Words>() {
                override fun areItemsTheSame(oldItem: Words, newItem: Words): Boolean =
                    oldItem.value == newItem.value

                override fun areContentsTheSame(oldItem: Words, newItem: Words): Boolean =
                    oldItem == newItem
            })
        joke_list.adapter = adapter

        activity?.let {
            updateList(it, adapter)


        }

        val observer = Observer<String> {
            activity?.let { it1 -> updateList(it1, adapter) }
        }
        sharedPreViewModel.upDatedId.observe(this, observer)
    }

    /**
     * when add new joke ,delete a joke, refresh the list
     */
    private fun updateList(
        it: FragmentActivity,
        adapter: BaseBindingRecycleAdapter<Words>
    ) = CoroutineScope(Dispatchers.IO).launch {
        activity?.let { it1 -> sharedPreViewModel.getWordsList(it1) }
        withContext(Dispatchers.Main){
            context?.let { _ -> sharedPreViewModel.words?.observe(this@StatisticFragment, adapter::submitList) }
        }
    }


}