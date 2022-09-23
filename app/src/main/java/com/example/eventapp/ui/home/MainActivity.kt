package com.example.eventapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
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

        viewModel =
            ViewModelProvider(this, MyViewModelFactory(EventRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        binding.rvEvents.adapter = adapter

        viewModel.eventList.observe(this, Observer {
            Log.d(TAG, "onCreate: $it")
            adapter.setEventList(it)
            binding.pbRecycler.visibility = View.GONE  // Set Progressbar Invisible
        })
        viewModel.errorMessage.observe(this, Observer {

        })

        viewModel.getAllEvents()

        // SharedPreferences Start
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Show name on activity
        val username = sharedPreferences.getString("user_Name", null)
        binding.lbName.text = username

        // Change User and Email
        binding.ivAccount.setOnClickListener {
            var userFirstRun = false
            editor.putBoolean("user_FirstRun", userFirstRun).apply()
            editor.putString("user_Name", null).apply()
            editor.putString("user_Email", null).apply()
            var intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    // Double Press to Exit
    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}

// TODO CHECK INTERNET CONNECTION
