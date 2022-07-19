package com.yigitcan.mvvmmovielister.service

import com.yigitcan.mvvmmovielister.model.MovieResponseModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface MovieAPI {
    @GET("discover/movie")
    fun loadChanges(
        @Query("language") language: String?,
        @Query("api_key") apiKey: String?
    ): Call<MovieResponseModel?>?
}