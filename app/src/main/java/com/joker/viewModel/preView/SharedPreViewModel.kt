package com.joker.viewModel.preView

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.joker.data.dto.JokeInfo
import com.joker.data.repository.JokeGetRepository
import com.joker.utils.dataBase.DBHelper
import com.joker.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Yan
 * viewModel used for data processing
 */
@HiltViewModel
class SharedPreViewModel @Inject constructor(private val jokeGetRepository: JokeGetRepository) :
    BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val jokeInfo: MutableLiveData<JokeInfo> by lazy { MutableLiveData<JokeInfo>() }
    var jokes: LiveData<PagedList<JokeInfo>>? = null
    //if add a new joke, notify the joke list
    var upDatedId = MutableLiveData<String>()

    fun getJokeFromServer(context: Context) {
        viewModelScope.launch {
            jokeGetRepository.requestJoke().collect {
                it.data?.joke?.apply {
                    val dao = DBHelper.getDataBase(context)?.jokeDao()
                    dao?.queryWithKey<JokeInfo>(it.data.id, {
                        //if database contains this joke, fetch again
                        getJokeFromServer(context)
                    }, {
                        jokeInfo.value = it.data
                    })
                }
            }
        }
    }

    suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>? {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                jokes = jokeGetRepository.getLocalJokes(context)
            }
        }
        return jokes
    }

    fun addToFavorite(context: Context) {
        jokeInfo.value?.let {
            jokeGetRepository.addToFavorite(it, context) {
                upDatedId.value = it.id
            }
        }
    }


    fun deleteJoke(joke: JokeInfo, context: Context) {
        jokeGetRepository.deleteJoke(joke,context)
    }



}

