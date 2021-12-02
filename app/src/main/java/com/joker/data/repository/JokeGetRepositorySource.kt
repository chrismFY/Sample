package com.joker.data.repository


import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by Yan
 */

interface JokeGetRepositorySource {
    suspend fun requestJoke(): Flow<Resource<JokeInfo>>
}
