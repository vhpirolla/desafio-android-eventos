package com.example.eventapp.ui.description

import androidx.lifecycle.ViewModel
import com.example.eventapp.data.remote.UserModel
import com.example.eventapp.retrofit.CheckinService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DescriptionViewModel: ViewModel() {
        fun checkUser(userModel: UserModel, onResult: (UserModel?) -> Unit){
            val retrofit = CheckinService.ServiceBuilder.buildService(CheckinService::class.java)
            retrofit.postCheckin(userModel).enqueue(
                object : Callback<UserModel> {
                    override fun onFailure(call: Call<UserModel>, t: Throwable) {
                        onResult(null)
                    }
                    override fun onResponse( call: Call<UserModel>, response: Response<UserModel>) {
                        val userResponse = response.body()
                        onResult(userResponse)
                    }
                }
            )
        }
    }