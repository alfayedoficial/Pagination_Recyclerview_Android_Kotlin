package com.alialfayed.pagination.kotlin.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.alialfayed.pagination.kotlin.api.API
import com.alialfayed.pagination.kotlin.api.ApiClient
import com.alialfayed.pagination.kotlin.model.ResponseTopMovies
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by ( Eng Ali Al Fayed)
 * Class do :
 * Date 10/7/2020 - 10:14 AM
 */
class RetrofitRepository private constructor (app: Application) {

    private var instanceApi: API

    init {
        ApiClient.init(app)
        instanceApi= ApiClient.instance
    }

    companion object{
        private var retrofitRepository: RetrofitRepository?=null
        @Synchronized
        fun getInstance(app:Application): RetrofitRepository? {
            if (retrofitRepository == null) {
                retrofitRepository = RetrofitRepository(app)
            }
            return retrofitRepository
        }
    }

    fun loadPage(topMoviesResponse: MutableLiveData<Any>, page : Int) {
        instanceApi.getTopRatedMovies(pageIndex = page).enqueue(object : Callback<ResponseTopMovies> {
            override fun onFailure(call: Call<ResponseTopMovies>, t: Throwable) {
                topMoviesResponse.value = t
            }

            override fun onResponse(
                call: Call<ResponseTopMovies>,
                response: Response<ResponseTopMovies>
            ) {

               if (response.isSuccessful){
                   topMoviesResponse.value = response.body()
               }else{
                   topMoviesResponse.value = "error"
               }
            }
        })
    }






}