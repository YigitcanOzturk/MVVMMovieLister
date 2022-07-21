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
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.databinding.SimilarListBinding
import com.yigitcan.mvvmmovielister.model.Movie
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class SimilarAdapter(private val similarList: ArrayList<Movie>, private var mContext: Context?): RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder>(), Filterable{

    var similarFilterList = ArrayList<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val itemBinding = SimilarListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SimilarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val currentItem = similarFilterList[position]
        val baseurl = "https://image.tmdb.org/t/p/w500"
        Picasso.with(mContext).load(baseurl + currentItem.posterPath).into(holder.itemBinding.imageViewSimilar)
        holder.itemBinding.txtTitleSimilar.text = currentItem.title
        holder.itemBinding.txtDescriptionSimilar.text = currentItem.overView
        holder.itemBinding.txtDateSimilar.text = "Release Date\n"+currentItem.releaseDate
        holder.itemBinding.txtVoteSimilar.text = "Vote Average\n"+round(currentItem.voteAverage).toString()
        holder.itemBinding.cardViewSimilar.setOnClickListener {
            val clipboardManager = holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE)  as ClipboardManager
            val clipData = ClipData.newPlainText("title", currentItem.title)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(mContext, "Title copied to clipboard", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return similarFilterList.size
    }

    init {
        similarFilterList = similarList
    }

    private fun round(input: Double): Double {
        return (input * 100.0).roundToInt() / 100.0        // 0.85
    }

    inner class SimilarViewHolder(internal val itemBinding: SimilarListBinding) :  RecyclerView.ViewHolder(itemBinding.root)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                similarFilterList = if (charSearch.isEmpty()) {
                    similarList
                } else {
                    val resultList = ArrayList<Movie>()
                    for (row in similarList) {
                        if (row.title!!.lowercase(Locale.getDefault())
                                .contains(constraint.toString().lowercase(Locale.getDefault()))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = similarFilterList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                similarFilterList = results?.values as ArrayList<Movie>
                notifyDataSetChanged()
            }
        }
    }
}