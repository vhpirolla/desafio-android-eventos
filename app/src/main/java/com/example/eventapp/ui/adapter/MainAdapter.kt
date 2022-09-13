package com.example.eventapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eventapp.data.remote.EventsModel
import com.example.eventapp.databinding.EventItemBinding

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
        holder.binding.lbEventDay.text = events.date
        holder.binding.lbEventMonth.text = events.date
        holder.binding.lbEventLocal.text = events.longitude
        holder.binding.lbEventTime.text = events.date
        Glide.with(holder.itemView.context).load(events.image).into(holder.binding.ivEventImage)
    }

    override fun getItemCount(): Int {
        return events.size
    }
}

class MainViewHolder(val binding: EventItemBinding) : RecyclerView.ViewHolder(binding.root) {

}