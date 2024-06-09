package com.example.github_user_app.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github_user_app.apiGithub.Client
import com.example.github_user_app.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setListFollowing(username: String) {
        Log.d("FollowingViewModel", "Mengambil daftar following untuk username: $username")
        Client.apiInstance.getFollowing(username).enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) {
                    Log.d("FollowingViewModel", "Berhasil mendapatkan daftar following")
                    listFollowing.postValue(response.body())
                }else{
                    Log.d("FollowingViewModel", "Gagal mendapatkan daftar following. Kode respons: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.e("FollowingViewModel", "Terjadi kesalahan saat memanggil API: ${t.message}")
            }

        })
    }

    fun getListFollowing(): LiveData<ArrayList<User>> {
        return listFollowing
    }

}