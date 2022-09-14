package com.example.eventapp.ui.adapter

import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventapp.data.remote.EventsModel
import com.example.eventapp.databinding.EventItemBinding
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
        val events = events[position]
        holder.binding.lbEventTitle.text = events.title
        holder.binding.lbEventDay.text = getDateDay(events.date)
        holder.binding.lbEventMonth.text = getDateMonth(events.date)
        holder.binding.lbEventLocal.text = events.longitude
        holder.binding.lbEventTime.text = getDateTime(events.date)
        Glide.with(holder.itemView.context).load(events.image).into(holder.binding.ivEventImage)
    }

    override fun getItemCount(): Int {
        return events.size
    }
}

class MainViewHolder(val binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {

}

fun getDateDay(s: String): String? {
    try {
        val sdf = SimpleDateFormat("dd")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getDateMonth(s: String): String? {
    try {
        val sdf = SimpleDateFormat("MMM")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}

fun getDateTime(s: String): String? {
    try {
        val sdf = SimpleDateFormat("HH:mm:ss")
        val netDate = Date(s.toLong() * 1000)
        return sdf.format(netDate)
    } catch (e: Exception) {
        return e.toString()
    }
}