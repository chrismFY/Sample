package com.joker.data.remoteService.service

import com.joker.data.dto.JokeInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by Yan
 */

interface JokeService {
    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String?): Call<ResponseBody?>?

    @Headers("Accept: application/json")
    @GET(".")
    suspend fun getJokeInfo():Response<JokeInfo>?
}
