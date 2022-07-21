package com.yigitcan.mvvmmovielister.ui.details

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.yigitcan.mvvmmovielister.adapter.SimilarAdapter
import com.yigitcan.mvvmmovielister.databinding.FragmentDetailsBinding
import com.yigitcan.mvvmmovielister.model.Genre
import com.yigitcan.mvvmmovielister.model.Movie

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val baseurl = "https://image.tmdb.org/t/p/w500"
    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var similarViewModel: SimilarViewModel
    private lateinit var similarAdapter: SimilarAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root



        detailsViewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        detailsViewModel.detailsMutableLiveData.observe(viewLifecycleOwner, detailsUpdateObserver)

        similarViewModel = ViewModelProvider(this)[SimilarViewModel::class.java]
        similarViewModel.similarMutableLiveData.observe(viewLifecycleOwner, similarUpdateObserver)


        detailsViewModel.select2.observe(viewLifecycleOwner) {
            binding.recyclerViewSimilar.visibility = it
            binding.cardViewDetails.visibility = it
            binding.textSimilarTitle.visibility = it
            binding.searchTextSimilar.visibility = it
        }

        detailsViewModel.select1.observe(viewLifecycleOwner) {
            binding.textResult.visibility = it
        }

        val tt: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
             try {
                similarAdapter.filter.filter(s.toString())
             }
             catch (e: UninitializedPropertyAccessException) {
                e.printStackTrace()
             }
            }
        }
        binding.searchTextSimilar.addTextChangedListener(tt)

        return root
    }


    private var detailsUpdateObserver: Observer<ArrayList<Genre>?> =
        Observer<ArrayList<Genre>?> { detailsArrayList ->
            try {
                val lastIndex: Int = detailsArrayList.size - 1
                binding.txtTitleDetails.text = detailsArrayList[lastIndex].title
                binding.txtDescriptionDetails.text =
                    detailsArrayList[lastIndex].overView
                Picasso.with(context)
                    .load(baseurl + detailsArrayList[lastIndex].posterPath)
                    .into(binding.imageViewDetails)
                val separatedString = detailsArrayList.joinToString (separator = "") { "${it.genreName}\n" }
                binding.txtGenreDetails.text = "Genres\n$separatedString"
                binding.txtStatusDetails.text = "Status\n"+detailsArrayList[lastIndex].status
                binding.txtDateDetails.text = "Release Date\n"+detailsArrayList[lastIndex].releaseDate
                binding.txtVoteDetails.text = "Vote Average\n"+detailsArrayList[lastIndex].voteAverage.toString()
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

    private var similarUpdateObserver: Observer<ArrayList<Movie>?> =
        Observer<ArrayList<Movie>?> { similarArrayList ->
            try {
                similarAdapter = SimilarAdapter(similarArrayList, context)
                binding.recyclerViewSimilar.layoutManager = LinearLayoutManager(context)
                binding.recyclerViewSimilar.adapter = similarAdapter
            }
            catch(e: Exception){
                e.printStackTrace()
            }
        }

    override fun onResume() {
        super.onResume()
        binding.searchTextSimilar.text.clear()
        detailsViewModel.loadData()
        detailsViewModel.control1()
        detailsViewModel.control2()
        similarViewModel.loadSimilarData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}