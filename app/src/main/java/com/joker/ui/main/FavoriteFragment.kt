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
import kotlinx.coroutines.withContext

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FavoriteFragmentBinding
    private val sharedPreViewModel: SharedPreViewModel by activityViewModels()
    lateinit var adapter: BaseBindingRecycleAdapter<JokeInfo>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)
        activity?.let { sharedPreViewModel.getJokeFromServer(it) }
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            jokeListViewModel = sharedPreViewModel
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter = BaseBindingRecycleAdapter(BR.jokeInfo, R.layout.item_joke_list,
            object : DiffUtil.ItemCallback<JokeInfo>() {
                override fun areItemsTheSame(oldItem: JokeInfo, newItem: JokeInfo): Boolean =
                    oldItem.index == newItem.index

                override fun areContentsTheSame(oldItem: JokeInfo, newItem: JokeInfo): Boolean =
                    oldItem == newItem
            })
        joke_list.adapter = adapter
        adapter.setOnBinder { baseBindingHolder, i ->
            val binding = baseBindingHolder.binding as ItemJokeListBinding
            binding.shareInItem.setOnClickListener {
                activity?.let { it1 -> sharedPreViewModel.shareJoke(it1,binding.jokeInfo) }
            }
        }
        activity?.let {
            updateList(it, adapter)
        }

        initSwipeToDelete()
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
        adapter: BaseBindingRecycleAdapter<JokeInfo>
    ) = CoroutineScope(Dispatchers.IO).launch {
        sharedPreViewModel.getLocalJokes(it)
        withContext(Dispatchers.Main) {
            sharedPreViewModel.jokes?.observe(this@FavoriteFragment, adapter::submitList)
        }
    }

    /**
     * slide delete item from database
     */
    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val bind = (viewHolder as BaseBindingHolder).binding as ItemJokeListBinding
                activity?.let {
                    sharedPreViewModel.deleteJoke(bind.jokeInfo!!, it)
                    bind.root.postDelayed({ updateList(it, adapter) }, 300)
                }

            }
        }).attachToRecyclerView(joke_list)
    }

}