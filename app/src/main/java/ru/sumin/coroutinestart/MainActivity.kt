package ru.sumin.coroutinestart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
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
            val jobCity = lifecycleScope.launch {
                val city = loadCity()
                binding.tvLocation.text = city
            }
            val jobTemp = lifecycleScope.launch {
                val temp = loadTemperature()
                binding.tvTemperature.text = temp.toString()
            }

            lifecycleScope.launch {
                jobCity.join()
                jobTemp.join()
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
