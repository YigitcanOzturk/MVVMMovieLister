package com.yigitcan.mvvmmovielister.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.databinding.FragmentDetailsBinding
import com.yigitcan.mvvmmovielister.model.Genre
import com.yigitcan.mvvmmovielister.model.Movie

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val baseurl = "https://image.tmdb.org/t/p/w500"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        detailsViewModel.genreMutableLiveData.observe(viewLifecycleOwner, detailsUpdateObserver)
        if(Movie.selectedMovieId==0){
            binding.textResult.visibility = View.VISIBLE
            binding.recyclerViewDetails.visibility = View.GONE
            binding.cardViewDetails.visibility = View.GONE
        }
        else {
            binding.textResult.visibility = View.GONE
            binding.recyclerViewDetails.visibility = View.VISIBLE
            binding.cardViewDetails.visibility = View.VISIBLE
        }
       /* val textView: TextView = binding.textDetails
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    private var detailsUpdateObserver: Observer<ArrayList<Genre>?> =
        Observer<ArrayList<Genre>?> { detailsArrayList ->
            binding.txtTitleDetails.text = detailsArrayList[detailsArrayList.size -1].title
            binding.txtDescriptionDetails.text = detailsArrayList[detailsArrayList.size -1].overView
            Picasso.with(context).load(baseurl + detailsArrayList[detailsArrayList.size -1].posterPath).into(binding.imageViewDetails)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}