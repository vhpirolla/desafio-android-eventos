package com.example.eventapp.ui.home

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
import com.example.eventapp.ui.adapter.MainAdapter

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
        viewModel = ViewModelProvider(this, MyViewModelFactory(EventRepository(retrofitService))).get(MainViewModel::class.java)

        binding.rvEvents.adapter = adapter

        viewModel.eventList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setEventList(it)
        })
        viewModel.errorMessage.observe(this, Observer {
        })
        viewModel.getAllEvents()
    }
}

// TODO Progressbar / Loader on Retrofit Loading
