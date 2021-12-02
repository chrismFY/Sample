package com.joker.model

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.joker.data.dto.JokeInfo
import com.joker.utils.NetworkConnectivity
import com.joker.data.remoteService.ServiceGenerator
import javax.inject.Inject
import com.joker.data.dto.Resource
import com.joker.data.remoteService.service.JokeService
import com.joker.utils.dataBase.BBDataBase
import com.joker.utils.dataBase.DBHelper

/**
 * Created by Yan
 * used for operation of database
 */

class LocalJokeModel @Inject
constructor() {
    suspend fun getLocalJokes(context: Context): LiveData<PagedList<JokeInfo>>? {
        DBHelper.getDataBase(context)?.jokeDao()?.getAllRecords()
            ?.toLiveData(Config(pageSize = 10, enablePlaceholders = true, maxSize = 30))?.let {
            return it
        }
        return null
    }

    fun addToFavorite(joke: JokeInfo, context: Context,success: ()->Unit) {
        joke.isFavorite = true
        updateJoke(joke,context,success)
    }

    fun updateJoke(joke: JokeInfo, context: Context,success: ()->Unit) {
        val dao = DBHelper.getDataBase(context)?.jokeDao()
        dao?.queryWithKey<JokeInfo>(joke.id,{
            Toast.makeText(context,"already added", Toast.LENGTH_SHORT).show()
        },{
            dao.insert(joke)
            success.invoke()

        })
    }

    fun deleteJoke(joke: JokeInfo, context: Context) {
        DBHelper.getDataBase(context)?.jokeDao()?.delete(joke)
    }

}
