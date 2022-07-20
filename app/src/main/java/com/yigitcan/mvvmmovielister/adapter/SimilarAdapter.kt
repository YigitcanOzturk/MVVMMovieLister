package com.yigitcan.mvvmmovielister.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.databinding.SimilarListBinding
import com.yigitcan.mvvmmovielister.model.Movie


class SimilarAdapter(private val similarList: ArrayList<Movie>, private var mContext: Context?): RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val itemBinding = SimilarListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SimilarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val currentItem = similarList[position]
        val baseurl = "https://image.tmdb.org/t/p/w500"
        Picasso.with(mContext).load(baseurl + currentItem.posterPath).into(holder.itemBinding.imageViewSimilar)
        holder.itemBinding.txtTitleSimilar.text = currentItem.title
        holder.itemBinding.txtDescriptionSimilar.text = currentItem.overView
        holder.itemBinding.txtDateSimilar.text = currentItem.releaseDate
        holder.itemBinding.txtVoteSimilar.text = currentItem.voteAverage.toString()
    }

    override fun getItemCount(): Int {
        return similarList.size
    }

    inner class SimilarViewHolder(internal val itemBinding: SimilarListBinding) :  RecyclerView.ViewHolder(itemBinding.root)
}