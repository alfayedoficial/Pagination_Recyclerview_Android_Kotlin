package com.alialfayed.pagination.kotlin.api

import com.alialfayed.pagination.kotlin.model.ResponseTopMovies
import com.alialfayed.pagination.kotlin.utils.API_KEY
import com.alialfayed.pagination.kotlin.utils.APP_LANGUAGE
import com.alialfayed.pagination.kotlin.utils.MOVIE_TOP_RATED_API
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do : Api Interface of Retrofit Client
 * Date 11/23/2020 - 11:46 AM
 */
interface API {

    @GET(MOVIE_TOP_RATED_API)
    fun getTopRatedMovies(@Query("api_key") apiKey: String = API_KEY
                          , @Query("language") language: String = APP_LANGUAGE
                          , @Query("page") pageIndex: Int): Call<ResponseTopMovies>
}