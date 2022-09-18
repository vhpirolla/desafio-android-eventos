package com.example.eventapp.ui.description

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.eventapp.R
import com.example.eventapp.data.remote.EventsModel
import com.example.eventapp.databinding.ActivityDescriptionBinding
import com.example.eventapp.ui.adapter.getAddress
import java.text.SimpleDateFormat
import java.util.*

class DescriptionActivity : AppCompatActivity() {

    object Extras {
        const val EVENT = "EXTRA_EVENT"
    }

    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadEventFromExtra()
    }

    private fun loadEventFromExtra() {
        intent?.extras?.getParcelable<EventsModel>(Extras.EVENT)?.let {
            Glide.with(this).load(it.image).into(binding.ivEventDescriptionImage)
            binding.lbEventDescriptionTitle.text = it.title
            binding.lbEventDescriptionAbout.text = it.description
            binding.lbEventDescriptionLocal.text = getAddress(this, it.latitude, it.longitude)
            binding.lbEventDescriptionDate.text = getDateFullDate(it.date)
            binding.lbEventDescriptionPrice.text = "R$ " + it.price
        }
    }

    // TODO Read More Description

    fun getDateFullDate(s: String): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }
}