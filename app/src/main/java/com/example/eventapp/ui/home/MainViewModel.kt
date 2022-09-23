package com.example.eventapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventapp.data.remote.EventRepository
import com.example.eventapp.data.remote.EventsModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel constructor(private val repository: EventRepository): ViewModel() {

    val eventList = MutableLiveData<List<EventsModel>>()
    val errorMessage = MutableLiveData<String>()

        fun getAllEvents(){
            val response = repository.getAllEvents()
            response.enqueue(object: Callback<List<EventsModel>> {
                override fun onResponse(call: Call<List<EventsModel>>, response: Response<List<EventsModel>>) {
                    eventList.postValue(response.body())
                }

                override fun onFailure(call: Call<List<EventsModel>>, t: Throwable) {
                    errorMessage.postValue(t.message)
                }


            })
        }

}