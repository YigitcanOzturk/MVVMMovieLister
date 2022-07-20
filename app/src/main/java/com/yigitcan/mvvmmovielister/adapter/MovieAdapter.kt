package com.yigitcan.mvvmmovielister.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.databinding.MovieListBinding
import com.yigitcan.mvvmmovielister.model.Movie


class MovieAdapter(private val movieList: ArrayList<Movie>, private var mContext: Context?): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieList[position]
        val baseurl = "https://image.tmdb.org/t/p/w500"
        Picasso.with(mContext).load(baseurl + currentItem.posterPath).into(holder.itemBinding.imageView)
        holder.itemBinding.txtTitle.text = currentItem.title
        holder.itemBinding.txtDescription.text = currentItem.overView
        holder.itemBinding.txtDate.text = currentItem.releaseDate
        holder.itemBinding.txtVote.text = currentItem.voteAverage.toString()
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MovieViewHolder(internal val itemBinding: MovieListBinding) :  RecyclerView.ViewHolder(itemBinding.root)
}