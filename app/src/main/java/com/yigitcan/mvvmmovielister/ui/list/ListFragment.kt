package com.yigitcan.mvvmmovielister.ui.list

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
import com.yigitcan.mvvmmovielister.adapter.MovieAdapter
import com.yigitcan.mvvmmovielister.databinding.FragmentListBinding
import com.yigitcan.mvvmmovielister.model.Movie

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listViewModel = ViewModelProvider(this)[ListViewModel::class.java]
        listViewModel.movieMutableLiveData.observe(viewLifecycleOwner, movieListUpdateObserver)

        val tt: TextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                try {
                    movieAdapter.filter.filter(s.toString())
                }
                catch (e: UninitializedPropertyAccessException) {
                    e.printStackTrace()
                }
            }
        }
        binding.searchTextList.addTextChangedListener(tt)

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

    override fun onResume() {
        super.onResume()
        binding.searchTextList.text.clear()
        listViewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}