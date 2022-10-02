package com.example.eventapp.data.remote

import com.example.eventapp.retrofit.EventsService

class EventRepository constructor(private val eventsService: EventsService) {

    fun getAllEvents() = eventsService.getAllEvents()

}