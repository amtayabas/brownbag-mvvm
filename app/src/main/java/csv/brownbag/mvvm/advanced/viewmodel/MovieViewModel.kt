package csv.brownbag.mvvm.advanced.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import csv.brownbag.mvvm.advanced.model.Movie
import csv.brownbag.mvvm.advanced.repository.MovieRepository
import csv.brownbag.mvvm.advanced.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movieList = MutableLiveData<Resource<List<Movie>>>()
    val movieList get() = _movieList

    private val _movieListAsStateFlow = MutableStateFlow<Resource<List<Movie>>>(Resource.loading(null))
    val movieListAsStateFlow get() = _movieListAsStateFlow

    // NOTE: Live Data Implementation - producer and observer can be different classes
    fun liveDataGetAllMovies() {
        _movieList.postValue(Resource.loading(null))
        val response = repository.getAllMovies()
        response.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                _movieList.postValue(Resource.success(response.body()))
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                _movieList.postValue(Resource.error("Something went wrong. ${t.message}", null))
            }
        })
    }

    // NOTE: State Flow Implementation - producer and observer can be different classes
    fun stateFlowGetAllMovies() {
        viewModelScope.launch {
            movieListAsStateFlow.value = Resource.loading(null)
            val response = repository.getAllMovies()
            response.enqueue(object : Callback<List<Movie>> {
                override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                    movieListAsStateFlow.value = Resource.success(response.body())
                }

                override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                    movieListAsStateFlow.value = Resource.error("Something went wrong. ${t.message}", null)
                }
            })
        }
    }

    // NOTE: Flow Implementation - single fire
    fun flowGetAllMovies() = flow {
        emit(Resource.loading(null))
        try {
            emit(Resource.success(repository.flowGetAllMovies()))
        } catch (e: Exception) {
            emit(Resource.error("Something went wrong. ${e.message}", null))
        }
    }.flowOn(Dispatchers.IO)


}