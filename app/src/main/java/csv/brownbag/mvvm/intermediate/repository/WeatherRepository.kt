package csv.brownbag.mvvm.intermediate.repository

import csv.brownbag.mvvm.common.Constants
import csv.brownbag.mvvm.intermediate.api.RetrofitInstance
import csv.brownbag.mvvm.intermediate.model.WeatherResponse
import retrofit2.Call

class WeatherRepository {

    fun getWeatherDetails(lat: String, long: String): Call<WeatherResponse> {
        return RetrofitInstance.api.getCurrentWeatherData(lat, long, Constants.WEATHER_APP_ID)
    }

}