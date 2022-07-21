package com.yigitcan.mvvmmovielister.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.databinding.BottomSheetDialogLayoutBinding
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
        holder.itemBinding.txtDate.text = "Release Date\n"+currentItem.releaseDate
        holder.itemBinding.txtVote.text = "Vote Average\n"+currentItem.voteAverage.toString()
        holder.itemBinding.cardView.setOnClickListener {
           showDialog(it.rootView.context,currentItem.videoId)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    private fun showDialog(context: Context?,selectedMovie: Int) {
        val dialog = BottomSheetDialog(context!!)
        val binding: BottomSheetDialogLayoutBinding = BottomSheetDialogLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.add.setOnClickListener {
            Movie.selectedMovieId = selectedMovie
            dialog.dismiss()
        }
        binding.remove.setOnClickListener {
            Movie.selectedMovieId = 0
            dialog.dismiss()
        }
        binding.cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    inner class MovieViewHolder(internal val itemBinding: MovieListBinding) :  RecyclerView.ViewHolder(itemBinding.root)
}