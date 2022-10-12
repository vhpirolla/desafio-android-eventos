package com.example.eventapp.ui.description

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.FileProvider
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eventapp.R
import com.example.eventapp.data.remote.EventsModel
import com.example.eventapp.data.remote.UserModel
import com.example.eventapp.databinding.ActivityDescriptionBinding
import com.example.eventapp.ui.home.adapter.getAddress
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class DescriptionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding
    private lateinit var viewModel: DescriptionViewModel

    object Extras {
        const val EVENT = "EXTRA_EVENT"
    }

    // start var eventTitle to Share
    var eventTitle = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DescriptionViewModel::class.java) // Ver se Funciona

        // got all info from recyclerview
        loadEventFromExtra()

        // hide share button on screenshot
        binding.ivButtonShare.setOnClickListener {
            binding.ivButtonShare.isVisible = false
            shareEvent()
            binding.ivButtonShare.isVisible = true
        }
    }

    private fun loadEventFromExtra() {
        intent?.extras?.getParcelable<EventsModel>(Extras.EVENT)?.let {
            Glide.with(this)
                .load(it.image)
                .placeholder(R.mipmap.placeholder)
                .into(binding.ivEventDescriptionImage)
            binding.lbEventDescriptionTitle.text = it.title
            binding.lbEventDescriptionAbout.text = it.description
            binding.lbEventDescriptionLocal.text = getAddress(this, it.latitude, it.longitude)
            binding.lbEventDescriptionDate.text = getDateFullDate(it.date)
            binding.lbEventDescriptionPrice.text = "R$ " + it.price
            eventTitle = it.title
            val eventId = it.id

            binding.btnBuy.setOnClickListener {
                val sharedPreferences =
                    getSharedPreferences("userPreferences", MODE_PRIVATE)
                val name = sharedPreferences.getString("user_Name", null).toString()
                val email = sharedPreferences.getString("user_Email", null).toString()
                val userData = UserModel(eventId,name,email)
                viewModel.checkUser(userData)
            }
        }
    }


    fun getDateFullDate(s: String): String? {
        try {
            val sdf = SimpleDateFormat("dd/MM/yyyy - HH:mm")
            val netDate = Date(s.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun shareEvent() {
        val sdf = SimpleDateFormat("yyyy-mm-dd_hh:mm:ss")
        val now = sdf.format(Date())
        val path = getExternalFilesDir(null)!!.absolutePath + "/" + now + ".jpg"
        val bitmap = Bitmap.createBitmap(
            binding.lyActivityDescription.width,
            binding.lyActivityDescription.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        binding.lyActivityDescription.draw(canvas)
        val imagefile = File(path)
        val outputStream = FileOutputStream(imagefile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        val URI = FileProvider.getUriForFile(
            applicationContext,
            "com.example.eventapp.android.fileprovider",
            imagefile
        )

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT, eventTitle)
        intent.putExtra(Intent.EXTRA_STREAM, URI)
        intent.type = "text/plain"
        startActivity(intent)
    }

}


    // TODO Get all activity area

    // TODO FINISH POST