package com.yigitcan.mvvmmovielister.ui.details

import android.os.Bundle
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
        }

        detailsViewModel.select1.observe(viewLifecycleOwner) {
            binding.textResult.visibility = it
        }

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
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

    private var similarUpdateObserver: Observer<ArrayList<Movie>?> =
        Observer<ArrayList<Movie>?> { similarArrayList ->
            similarAdapter = SimilarAdapter(similarArrayList,context)
            binding.recyclerViewSimilar.layoutManager = LinearLayoutManager(context)
            binding.recyclerViewSimilar.adapter = similarAdapter
        }

    override fun onResume() {
        super.onResume()
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