package com.example.github_user_app.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.apiGithub.Client
import com.example.github_user_app.data.model.User
import com.example.github_user_app.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val listUsers = MutableLiveData<List<User>>()

    fun setSearchUsers(query: String) {
        Log.d("Query", "Search query: $query")
        Client.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<UserResponse> {

                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        val userResponse = response.body()
                        Log.d("UserResponse", "Number of users received: ${userResponse?.items?.size}")
                        listUsers.postValue(userResponse?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }

    suspend fun loadPopularUsers() {
        try {
            val response = Client.apiInstance.getTopUsers(perPage = 10, sinceUserId = 0)
            val topUsers = response
            listUsers.postValue(topUsers)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getSearchUsers(): LiveData<List<User>> {
        return listUsers
    }

}

