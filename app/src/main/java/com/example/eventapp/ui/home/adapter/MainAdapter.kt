package com.example.eventapp.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventapp.R
import com.example.eventapp.data.remote.EventsModel
import com.example.eventapp.databinding.EventItemBinding
import com.example.eventapp.ui.description.DescriptionActivity
import java.text.SimpleDateFormat
import java.util.*

class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {

    var events = mutableListOf<EventsModel>()

    fun setEventList(events: List<EventsModel>) {
        this.events = events.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val context: Context = holder.itemView.context

        val events = events[position]
        holder.binding.lbEventTitle.text = events.title
        holder.binding.lbEventDay.text = getDateDay(events.date)
        holder.binding.lbEventMonth.text = getDateMonth(events.date)
        holder.binding.lbEventLocal.text = getAddress(holder.itemView.context,events.latitude, events.longitude)
        holder.binding.lbEventTime.text = getDateTime(events.date)
        Glide.with(holder.itemView.context)
            .load(events.image)
            .placeholder(R.mipmap.placeholder)
            .into(holder.binding.ivEventImage)

        holder.itemView.setOnClickListener {
            var intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra(DescriptionActivity.Extras.EVENT, events)
            context.startActivity(intent)
        }
    }

    // TODO Return Image when Blank on API

    override fun getItemCount(): Int {
        return events.size
    }
}

class MainViewHolder(val binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {

}

fun getDateDay(s: String): String? {
    try {
        val sdf = SimpleDateFormat("dd")
        sdf.timeZone = TimeZone.getDefault()
        val netDate = Date(s.toLong())
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getDateMonth(s: String): String? {
    try {
        val sdf = SimpleDateFormat("MMM")
        sdf.timeZone = TimeZone.getDefault()
        val netDate = Date(s.toLong())
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getDateTime(s: String): String? {
    try {
        val sdf = SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getDefault()
        val netDate = Date(s.toLong())
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getAddress(context: Context,lat: String, long: String): String {
    val cityName: String
    val geoCoder = Geocoder(context, Locale.getDefault())
    val Address = geoCoder.getFromLocation(lat.toDouble(),long.toDouble(),1)

    cityName = Address[0].subAdminArea
    return cityName
}