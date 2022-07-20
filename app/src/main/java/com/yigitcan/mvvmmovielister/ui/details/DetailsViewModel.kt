package com.yigitcan.mvvmmovielister.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.GsonBuilder
import com.yigitcan.mvvmmovielister.model.DetailsResponseModel
import com.yigitcan.mvvmmovielister.model.Genre
import com.yigitcan.mvvmmovielister.model.Movie
import com.yigitcan.mvvmmovielister.service.DetailsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsViewModel : ViewModel(), Callback<DetailsResponseModel?> {
    var genreMutableLiveData: MutableLiveData<ArrayList<Genre>?> = MutableLiveData()
    private var genreArrayList: ArrayList<Genre>? = null
    private fun init() {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val detailsAPI = retrofit.create(DetailsAPI::class.java)
        val call: Call<DetailsResponseModel?>? = detailsAPI.loadChanges(Movie.selectedMovieId,"en-US",API_KEY)
        call?.enqueue(this)
    }

    override fun onResponse(call: Call<DetailsResponseModel?>?, response: Response<DetailsResponseModel?>) {
        try {
            genreArrayList = ArrayList()
            genreArrayList = response.body()?.genre
            genreArrayList!![genreArrayList?.size!! -1].posterPath = response.body()?.posterPath
            genreArrayList!![genreArrayList?.size!! -1].overView = response.body()?.overView
            genreArrayList!![genreArrayList?.size!! -1].releaseDate = response.body()?.releaseDate
            genreArrayList!![genreArrayList?.size!! -1].voteAverage = response.body()?.voteAverage
            genreArrayList!![genreArrayList?.size!! -1].status = response.body()?.status
            genreArrayList!![genreArrayList?.size!! -1].voteCount = response.body()?.voteCount
            genreArrayList!![genreArrayList?.size!! -1].title = response.body()?.title
            genreArrayList!![genreArrayList?.size!! -1].videoID = response.body()?.id
            genreMutableLiveData.value = genreArrayList
        }
        catch (e: NullPointerException) {
            e.printStackTrace()
            println(response.errorBody().toString() + "error")
        }
    }

    override fun onFailure(call: Call<DetailsResponseModel?>?, t: Throwable) {
        t.printStackTrace()
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "45e72af51ad7bb107f12e61387040e94"
    }

    init {

        // we call the Rest API in init method
        init()
    }

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is details Fragment"
    }
    val text: LiveData<String> = _text */
}