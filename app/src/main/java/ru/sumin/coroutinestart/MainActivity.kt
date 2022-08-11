package ru.sumin.coroutinestart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sumin.coroutinestart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            binding.progress.isVisible = true
            binding.buttonLoad.isEnabled = false
            val deferredCity: Deferred<String> = lifecycleScope.async {
                loadCity()
            }
            val deferredTemp: Deferred<Int> = lifecycleScope.async {
                loadTemperature()
            }

            lifecycleScope.launch {
                val city = deferredCity.await()
                val temp = deferredTemp.await()
                binding.tvLocation.text = city
                binding.tvTemperature.text = temp.toString()
                Toast.makeText(
                    this@MainActivity,
                    "City: $city, temp: $temp",
                    Toast.LENGTH_SHORT
                ).show()
                binding.progress.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Moscow"
    }

    private suspend fun loadTemperature(): Int {
        delay(2000)
        return 17
    }
}
