package com.example.eventapp.ui.description

import androidx.lifecycle.ViewModel
import com.example.eventapp.data.remote.UserModel
import com.example.eventapp.retrofit.CheckinService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionViewModel: ViewModel() {

    fun checkUser(userData: UserModel) {
        val retrofit = CheckinService.ServiceBuilder.buildService(CheckinService::class.java)
        retrofit.postCheckin(userData).enqueue(
            object : Callback<UserModel> {
                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    val s = ""
                }

                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    val s = ""
                }
            }
        )
    }
}