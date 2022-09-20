package com.example.eventapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventapp.R
import com.example.eventapp.data.remote.EventRepository
import com.example.eventapp.data.remote.MyViewModelFactory
import com.example.eventapp.databinding.ActivityMainBinding
import com.example.eventapp.retrofit.RetrofitService
import com.example.eventapp.ui.home.adapter.MainAdapter
import com.example.eventapp.ui.onboarding.OnboardingActivity

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel
    private val retrofitService = RetrofitService.getInstance()

    val adapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        binding.ivAccount.setOnClickListener{
            var userFirstRun = false
            editor.putBoolean("user_FirstRun", userFirstRun).apply()
            var intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }

        viewModel = ViewModelProvider(this, MyViewModelFactory(EventRepository(retrofitService))).get(MainViewModel::class.java)

        binding.rvEvents.adapter = adapter

        viewModel.eventList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setEventList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
        })
        viewModel.getAllEvents()

        val username = sharedPreferences.getString("user_Name",null)
        binding.lbName.text = username

    }
}

// TODO Progressbar / Loader on Retrofit Loading
