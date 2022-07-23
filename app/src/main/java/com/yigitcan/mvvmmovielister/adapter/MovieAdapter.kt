package com.yigitcan.mvvmmovielister.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.R
import com.yigitcan.mvvmmovielister.databinding.BottomSheetDialogLayoutBinding
import com.yigitcan.mvvmmovielister.databinding.MovieListBinding
import com.yigitcan.mvvmmovielister.model.Movie
import java.util.*
import kotlin.collections.ArrayList


class MovieAdapter(private val movieList: ArrayList<Movie>, private var mContext: Context?): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(),Filterable{

    var movieFilterList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movieFilterList[position]
        val baseurl = "https://image.tmdb.org/t/p/w500"
        Picasso.with(mContext).load(baseurl + currentItem.posterPath).into(holder.itemBinding.imageView)
        holder.itemBinding.txtTitle.text = currentItem.originalTitle
        holder.itemBinding.txtDescription.text = currentItem.overView
        holder.itemBinding.txtDate.text = "Release Date\n"+currentItem.releaseDate
        holder.itemBinding.txtVote.text = "Vote Average\n"+currentItem.voteAverage.toString()
        holder.itemBinding.cardView.setOnClickListener {
           showDialog(it.rootView.context,currentItem.videoId,currentItem.originalTitle.toString())
        }
    }

    override fun getItemCount(): Int {
        return movieFilterList.size
    }

    init {
        movieFilterList = movieList
    }

    private fun showDialog(context: Context?,selectedMovie: Int,selectedTitle: String) {
        val dialog = BottomSheetDialog(context!!)
        val binding: BottomSheetDialogLayoutBinding = BottomSheetDialogLayoutBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.add.setOnClickListener {
            Movie.selectedMovieId = selectedMovie
            Toast.makeText(mContext, it.resources.getString(R.string.movieSelected), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        binding.copy.setOnClickListener {
            val clipboardManager = it.context.getSystemService(Context.CLIPBOARD_SERVICE)  as ClipboardManager
            val clipData = ClipData.newPlainText("title", selectedTitle)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(mContext, it.resources.getString(R.string.titleCopied), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        binding.remove.setOnClickListener {
            Movie.selectedMovieId = 0
            Toast.makeText(mContext, it.resources.getString(R.string.movieUnselected), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        binding.cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    inner class MovieViewHolder(internal val itemBinding: MovieListBinding) :  RecyclerView.ViewHolder(itemBinding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                movieFilterList = if (charSearch.isEmpty()) {
                    movieList
                } else {
                    val resultList = ArrayList<Movie>()
                    for (row in movieList) {
                        if (row.originalTitle!!.lowercase(Locale.getDefault())
                                .contains(constraint.toString().lowercase(Locale.getDefault()))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = movieFilterList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                movieFilterList = results?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }
}