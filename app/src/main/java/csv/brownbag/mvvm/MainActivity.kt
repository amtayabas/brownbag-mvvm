package csv.brownbag.mvvm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import csv.brownbag.mvvm.advanced.api.RetrofitService
import csv.brownbag.mvvm.advanced.repository.MovieRepository
import csv.brownbag.mvvm.advanced.viewmodel.MovieViewModel
import csv.brownbag.mvvm.advanced.viewmodel.ViewModelFactory
import csv.brownbag.mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val movieViewModel: MovieViewModel by viewModels { ViewModelFactory(MovieRepository(RetrofitService.getInstance())) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NOTE: Once called in the parent activity, child Fragments observing the data will be notified
//        movieViewModel.getAllMoviesUsingLiveData()
//        movieViewModel.getAllMoviesUsingStateFlow()
    }

    override fun onResume() {
        super.onResume()
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navController = findNavController(R.id.nav_fragment)
        binding.bottomNavigationView.apply {
            NavigationUI.setupWithNavController(
                this,
                navController
            )
        }
    }
}