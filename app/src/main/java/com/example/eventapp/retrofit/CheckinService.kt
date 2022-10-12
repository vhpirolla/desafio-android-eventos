package com.example.eventapp.retrofit

import com.example.eventapp.data.remote.UserModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckinService {

    @POST("checkin")
    fun postCheckin(@Body userModel: UserModel): Call<UserModel>

    object ServiceBuilder {
        private val client = OkHttpClient.Builder().build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://5f5a8f24d44d640016169133.mockapi.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            fun <T> buildService(service: Class<T>): T {
                return retrofit.create(service)
            }
        }
    }