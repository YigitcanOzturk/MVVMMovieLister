package com.yigitcan.mvvmmovielister.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.yigitcan.mvvmmovielister.model.Movie
import com.yigitcan.mvvmmovielister.model.MovieResponseModel
import com.yigitcan.mvvmmovielister.service.MovieAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListViewModel : ViewModel(), Callback<MovieResponseModel?> {
    var movieMutableLiveData: MutableLiveData<ArrayList<Movie>?> = MutableLiveData()
    private var movieArrayList: ArrayList<Movie>? = null
    fun loadData() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(Movie.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val movieAPI = retrofit.create(MovieAPI::class.java)
        val call: Call<MovieResponseModel?>? = movieAPI.loadChanges("en-US", Movie.API_KEY)
        call?.enqueue(this)
    }

    override fun onResponse(call: Call<MovieResponseModel?>?, response: Response<MovieResponseModel?>) {
       try {
           movieArrayList = ArrayList()
           movieArrayList = response.body()?.movie
           movieMutableLiveData.setValue(movieArrayList)
       }
       catch (e: NullPointerException) {
           e.printStackTrace()
           println(response.errorBody().toString() + "error")
       }
    }

    override fun onFailure(call: Call<MovieResponseModel?>?, t: Throwable) {
        t.printStackTrace()
    }

    init {
        // we call the Rest API in init method
        loadData()
    }
}