package com.example.eventapp.data.remote

import com.example.eventapp.retrofit.RetrofitService

class EventRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllEvents() = retrofitService.getAllEvents()

}