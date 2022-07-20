package com.yigitcan.mvvmmovielister.service

import com.yigitcan.mvvmmovielister.model.MovieResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SimilarAPI {
    @GET("movie/{movie_id}/similar")
    fun loadChanges(
        @Path("movie_id") movieId: Int?,
        @Query("language") language: String?,
        @Query("api_key") apiKey: String?
    ): Call<MovieResponseModel?>?
}