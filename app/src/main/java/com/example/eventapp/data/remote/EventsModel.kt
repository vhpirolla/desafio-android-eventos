package com.example.eventapp.data.remote

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventsModel (
    val people: List<String>,
    val date: String,
    val description: String,
    val image: String,
    val longitude: String,
    val latitude: String,
    val price: String,
    val title: String,
    val id: Int
    ) : Parcelable