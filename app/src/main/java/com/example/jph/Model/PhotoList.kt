package com.example.jph.Model

import android.util.Log
import android.widget.BaseAdapter
import com.example.jph.network.NetworkService
import com.example.jph.POJOs.Photo
import com.example.jph.Saveable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotoList(
    val activity: Saveable,
    val photos: ArrayList<Photo>,
    val adapter: BaseAdapter,
    val id: Int
) {
    init {
        NetworkService.getJSONApi().getPhotosOfAlbum(id).enqueue(object : Callback<List<Photo>> {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.v("retrofit", "getPhotosOfAlbum call failed")
                activity.retrieve()
                adapter.notifyDataSetChanged()
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                photos.clear()
                photos.addAll(response.body() ?: return)
                activity.save()
                adapter.notifyDataSetChanged()
            }
        })
    }
}
