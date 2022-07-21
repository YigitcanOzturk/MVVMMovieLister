package com.yigitcan.mvvmmovielister.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yigitcan.mvvmmovielister.adapter.MovieAdapter
import com.yigitcan.mvvmmovielister.databinding.FragmentListBinding
import com.yigitcan.mvvmmovielister.model.Movie

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val listViewModel = ViewModelProvider(this)[ListViewModel::class.java]

        listViewModel.movieMutableLiveData.observe(viewLifecycleOwner, movieListUpdateObserver)

       /* val textView: TextView = binding.textList
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        } */

        return root
    }

    private var movieListUpdateObserver: Observer<ArrayList<Movie>?> =
        Observer<ArrayList<Movie>?> { movieArrayList ->
            try {
            movieAdapter = MovieAdapter(movieArrayList,context)
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = movieAdapter
            }
            catch(e: Exception){
             e.printStackTrace()
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}