package com.example.eventapp.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.eventapp.R
import com.example.eventapp.databinding.ActivityOnboardingBinding
import com.example.eventapp.ui.home.MainActivity

class OnboardingActivity : AppCompatActivity() {

    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkFirstRun()

        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        binding.btnSave.setOnClickListener {
            var userName = binding.edtName.text.toString()
            var userEmail = binding.edtEmail.text.toString()
            var userFirstRun = true

            if(userName.isNotEmpty() && userEmail.isValidEmail()) {
                editor.putString("user_Name",userName).apply()
                editor.putString("user_Email", userEmail).apply()
                editor.putBoolean("user_FirstRun", userFirstRun).apply()

                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(applicationContext, "Please, review name and e-mail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkFirstRun() {
        val sharedPreferences = getSharedPreferences("userPreferences", MODE_PRIVATE)
        val userFirstRun = sharedPreferences.getBoolean("user_FirstRun", false)
        if (userFirstRun) {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
        }
    }

    fun CharSequence?.isValidEmail():Boolean{
        return !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}