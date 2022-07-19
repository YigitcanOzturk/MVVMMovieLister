package com.yigitcan.mvvmmovielister.service

import com.yigitcan.mvvmmovielister.model.MovieResponseModel
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface MovieAPI {
    @GET("movie/1")
    fun loadChanges(
        @Query("language") language: String?,
        @Query("apiKey") apiKey: String?,
        @Query("page") page: Int = 1
    ): Call<MovieResponseModel?>?
}