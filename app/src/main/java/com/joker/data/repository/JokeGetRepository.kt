package com.joker.data.repository

import com.joker.data.dto.JokeInfo
import com.joker.data.dto.Resource
import com.joker.model.RemoteJokeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by Yan
 */

class JokeGetRepository @Inject constructor(private val remoteJokeModel: RemoteJokeModel) :
    JokeGetRepositorySource {

    override suspend fun requestJoke(): Flow<Resource<JokeInfo>> {
        return flow { emit(remoteJokeModel.requestJoke()) }.flowOn(Dispatchers.IO)
    }

}
