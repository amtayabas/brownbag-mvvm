package csv.brownbag.mvvm.intermediate.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import csv.brownbag.mvvm.databinding.FragmentIntermediateBinding
import csv.brownbag.mvvm.intermediate.repository.WeatherRepository
import csv.brownbag.mvvm.intermediate.viewmodel.WeatherViewModel

class IntermediateFragment : Fragment() {

    private lateinit var binding: FragmentIntermediateBinding

    private lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntermediateBinding.inflate(inflater, container, false)
        weatherViewModel = WeatherViewModel(WeatherRepository())

        setupView()
        setupObservers()

        return binding.root
    }

    private fun setupView() {
        with(binding) {
            btGetDetails.setOnClickListener {
                val lat = etLatitude.text.toString()
                val long = etLongitude.text.toString()
                weatherViewModel.getWeatherDetails(lat, long)
            }
        }
    }

    private fun setupObservers() {
        weatherViewModel.isLoading.observe(viewLifecycleOwner) { isVisible ->
            binding.piLoading.visibility = if (isVisible) View.VISIBLE else View.GONE
            binding.tvErrorMessage.visibility = View.GONE
            binding.tvDetails.visibility = View.GONE
        }

        weatherViewModel.onError.observe(viewLifecycleOwner) { error ->
            binding.tvErrorMessage.text = error
            binding.tvErrorMessage.visibility = View.VISIBLE
            binding.piLoading.visibility = View.GONE
        }

        weatherViewModel.weatherDetails.observe(viewLifecycleOwner) { response ->
            val stringBuilder = "Country: " +
                    response.sys.country +
                    "\n" +
                    "Place " +
                    response.name +
                    "\n" +
                    "Temperature: " +
                    response.main.temp +
                    "\n" +
                    "Temperature(Min): " +
                    response.main.tempMin +
                    "\n" +
                    "Temperature(Max): " +
                    response.main.tempMax +
                    "\n" +
                    "Humidity: " +
                    response.main.humidity +
                    "\n" +
                    "Pressure: " +
                    response.main.pressure

            binding.tvDetails.text = stringBuilder
            binding.tvDetails.visibility = View.VISIBLE
            binding.piLoading.visibility = View.GONE
        }

    }
}