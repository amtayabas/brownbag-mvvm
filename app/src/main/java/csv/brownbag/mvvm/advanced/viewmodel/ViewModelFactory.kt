package csv.brownbag.mvvm.advanced.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import csv.brownbag.mvvm.advanced.repository.MovieRepository

class ViewModelFactory constructor(private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) ->
                MovieViewModel(repository) as T
            else ->
                throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}