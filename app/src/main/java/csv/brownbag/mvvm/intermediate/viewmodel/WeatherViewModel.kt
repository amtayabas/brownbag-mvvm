package csv.brownbag.mvvm.intermediate.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import csv.brownbag.mvvm.intermediate.model.WeatherResponse
import csv.brownbag.mvvm.intermediate.repository.WeatherRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    private val _weatherDetails = MutableLiveData<WeatherResponse>()
    private val _onError = MutableLiveData<String>()

    val weatherDetails get() = _weatherDetails
    val isLoading get() = _isLoading
    val onError get() = _onError


    fun getWeatherDetails(lat: String, long: String) {
        _isLoading.postValue(true)
        repository.getWeatherDetails(lat, long).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    _weatherDetails.postValue(response.body()!!)
                } else {
                    _onError.postValue(response.message())
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _onError.postValue(t.message)
            }

        })
    }


}
