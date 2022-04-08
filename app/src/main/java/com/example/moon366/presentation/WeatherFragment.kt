package com.example.moon366.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.moon366.databinding.FragmentWeatherBinding
import com.example.moon366.toast
import org.json.JSONObject

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentWeatherBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue = Volley.newRequestQueue(requireContext())

        with(binding) {

            button.setOnClickListener {
                val stringReq = StringRequest(URL,
                    { response ->
                        var strResp = response.toString()
                        val jsonObj = JSONObject(strResp)

                        val weather = jsonObj.getJSONArray("weather")
                        for (i in 0 until weather.length()) {
                            var jsonInner: JSONObject = weather.getJSONObject(i)
                            var desc = jsonInner.get("description").toString()
                            description.text = "Description: $desc"
                        }

                        var temp = jsonObj.getJSONObject("main")
                            .getDouble("temp")
                            .toInt()
                            .toString()

                        var feelsLike = jsonObj.getJSONObject("main")
                            .getDouble("feels_like")
                            .toInt()
                            .toString()

                        var windSpeed = jsonObj.getJSONObject("wind")
                            .getDouble("speed")
                            .toString()

                        temperature.text = "Temperature: $temp"
                        feels.text = "Feels like: $feelsLike"
                        wind.text = "Wind speed: $windSpeed"

                    },
                    { requireContext().toast("No response received.") })

                queue.add(stringReq)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val URL = "https://api.openweathermap.org/data/2.5/weather?q=Minsk&appid=ea1b918de55d3aa06d5f25b23be3e359&units=metric"
    }
}