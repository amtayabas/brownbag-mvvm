package csv.brownbag.mvvm.advanced.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import csv.brownbag.mvvm.advanced.adapter.MovieAdapter
import csv.brownbag.mvvm.advanced.api.RetrofitService
import csv.brownbag.mvvm.advanced.repository.MovieRepository
import csv.brownbag.mvvm.advanced.utils.Status
import csv.brownbag.mvvm.advanced.viewmodel.MovieViewModel
import csv.brownbag.mvvm.advanced.viewmodel.ViewModelFactory
import csv.brownbag.mvvm.databinding.FragmentAdvancedBinding
import kotlinx.coroutines.flow.collectLatest

class AdvancedFragment : Fragment() {

    private lateinit var binding: FragmentAdvancedBinding

    // NOTE: #1 View Model declaration without using activityViewModels
//    private lateinit var movieViewModel: MovieViewModel

    private val movieViewModel: MovieViewModel by activityViewModels { ViewModelFactory(MovieRepository(RetrofitService.getInstance())) }

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAdvancedBinding.inflate(inflater, container, false)

        // NOTE: #1 View Model declaration without using activityViewModels
//        movieViewModel = ViewModelProvider(
//            this,
//            ViewModelFactory(MovieRepository(RetrofitService.getInstance()))
//        )[MovieViewModel::class.java]


        // NOTE: This can be called in the parent activity and the data will be readily available when the fragment is displayed
//        movieViewModel.liveDataGetAllMovies()
//        movieViewModel.stateFlowGetAllMovies()

        setupRecyclerView()
        setupObservers()
        getMovies()

        return binding.root
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.recyclerview.adapter = movieAdapter

        movieAdapter.setOnItemClickListener {
            findNavController().navigate(AdvancedFragmentDirections.actionAdvancedFragmentToMovieFragment(it))
        }
    }

    private fun setupObservers() {
        // Live Data
        movieViewModel.movieList.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.loading.visibility = View.GONE
                    movieAdapter.setMovieList(it.data!!)
                }
                Status.ERROR -> {
                    binding.loading.visibility = View.GONE
                    Snackbar.make(binding.coordinator, it.message!!, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        // State Flow
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            movieViewModel.movieListAsStateFlow.collectLatest {
                when (it.status) {
                    Status.LOADING -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE
                        movieAdapter.setMovieList(it.data!!)
                    }
                    Status.ERROR -> {
                        binding.loading.visibility = View.GONE
                        Snackbar.make(binding.coordinator, it.message!!, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun getMovies() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            movieViewModel.flowGetAllMovies().collect {
                when (it.status) {
                    Status.LOADING -> {
                        binding.loading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.loading.visibility = View.GONE
                        movieAdapter.setMovieList(it.data!!.body()!!)
                    }
                    Status.ERROR -> {
                        binding.loading.visibility = View.GONE
                        Snackbar.make(binding.coordinator, it.message!!, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

}