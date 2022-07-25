package csv.brownbag.mvvm.advanced.repository

import csv.brownbag.mvvm.advanced.api.RetrofitService


class MovieRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllMovies() = retrofitService.getAllMovies()

    suspend fun flowGetAllMovies() = retrofitService.flowGetAllMovies()

}