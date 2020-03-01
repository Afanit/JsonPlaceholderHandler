package com.example.jph.Model

import android.util.Log
import android.widget.BaseAdapter
import com.example.jph.network.NetworkService
import com.example.jph.POJOs.User
import com.example.jph.Saveable
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class UserList(val state: Saveable, val users: ArrayList<User>, val adapter: BaseAdapter) {
    init {
        NetworkService.getJSONApi().getAllUsers().enqueue(object : Callback,
            retrofit2.Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.v("retrofit", "getAllUsers call failed")
                state.retrieve()
                adapter.notifyDataSetChanged()
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                users.clear()
                users.addAll(response.body() ?: return)
                state.save()
                adapter.notifyDataSetChanged()
            }
        })
    }
}