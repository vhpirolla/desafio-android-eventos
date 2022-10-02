package com.example.eventapp.retrofit

import com.example.eventapp.data.remote.EventsModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface EventsService {
    @GET("events")
    fun getAllEvents(): Call<List<EventsModel>>

    companion object {
        var eventsService: EventsService? = null

        fun getInstance(): EventsService {
            if (eventsService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                eventsService = retrofit.create(EventsService::class.java)
            }
            return eventsService!!
        }
    }


}