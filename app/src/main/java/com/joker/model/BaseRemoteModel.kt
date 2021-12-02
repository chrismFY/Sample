package com.joker.model

import com.joker.data.dto.Resource
import com.joker.data.error.NETWORK_ERROR
import com.joker.data.error.NO_INTERNET_CONNECTION
import com.joker.utils.NetworkConnectivity
import retrofit2.Response
import java.io.IOException

/**
 * Created by Yan
 * Used for all model as pre-exception-processor and data wrapper, wrap the data with class:
 * @see Resource
 * All the data wrapped with Resource, it contains the data and possible error message.
 */

abstract class BaseRemoteModel<T> {

    protected suspend fun processCall(networkConnectivity: NetworkConnectivity, responseCall: suspend () -> Response<T>?): Resource<T> {
        if (!networkConnectivity.isConnected()) {
            return Resource.DataError(errorCode = NO_INTERNET_CONNECTION)
        }

        return try {
            val body = responseCall.invoke()?.body()
            if (responseCall.invoke()?.isSuccessful == true && body != null){
                Resource.Success(data = body)
            }else{
                Resource.DataError(errorCode = responseCall.invoke()?.code()?:NETWORK_ERROR)
            }
        } catch (e: IOException) {
            Resource.DataError(errorCode = NETWORK_ERROR)
        }
    }
}
