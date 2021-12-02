package com.joker.viewModel.preView

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.joker.data.dto.JokeInfo
import com.joker.viewModel.BaseViewModel
import com.joker.data.repository.JokeGetRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

import javax.inject.Inject

/**
 * Created by Yan
 */
@HiltViewModel
class PreViewModel @Inject constructor(private val jokeGetRepository: JokeGetRepository) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val jokeText : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun getJokeFromServer(){

        viewModelScope.launch {
            jokeGetRepository.requestJoke().collect {
                it.data?.joke?.apply {
                    jokeText.value = this

                    saveJoke(it.data)
                }
            }
        }
    }

    fun saveJoke(joke:JokeInfo){

    }






}

