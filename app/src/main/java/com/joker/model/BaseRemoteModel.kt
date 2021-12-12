package com.joker.model

import android.widget.Toast
import com.joker.App
import com.joker.data.dto.Resource
import com.joker.data.error.NETWORK_ERROR
import com.joker.data.error.NO_INTERNET_CONNECTION
import com.joker.utils.NetworkConnectivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

/**
 * Created by Yan
 * Used for all model as pre-exception-processor and data wrapper, wrap the data with class:
 * @see Resource
 * All the data wrapped with Resource, it contains the data and possible error message.
 */

abstract class BaseRemoteModel<T> {

    protected suspend fun processCall(networkConnectivity: NetworkConnectivity, responseCall: suspend () -> Response<T>?,onfail:(Resource<T>)->Unit = {}): Resource<T> {
        if (!networkConnectivity.isConnected()) {
            return Resource.DataError(errorCode = NO_INTERNET_CONNECTION)
        }

        return try {
            val body = responseCall.invoke()?.body()
            if (responseCall.invoke()?.isSuccessful == true && body != null){
                withContext(Dispatchers.Main){
//                    Toast.makeText(App.appContext,"success", Toast.LENGTH_SHORT).show()
                }
                Resource.Success(data = body)
            }else{
                val result:Resource<T> = Resource.DataError(errorCode = responseCall.invoke()?.code()?:NETWORK_ERROR)
                onfail.invoke(result)
                withContext(Dispatchers.Main) {
//                    Toast.makeText(App.appContext, "erro code:${result.errorCode}", Toast.LENGTH_SHORT).show()
                }
                result
            }
        } catch (e: IOException) {
            val result:Resource<T> = Resource.DataError(errorCode = NETWORK_ERROR)
            onfail.invoke(result)
            withContext(Dispatchers.Main) {
//                Toast.makeText(App.appContext, "erro code:${result.errorCode}", Toast.LENGTH_SHORT).show()
            }
            result

        }
    }
}
