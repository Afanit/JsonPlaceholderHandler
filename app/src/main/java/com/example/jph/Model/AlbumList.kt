package com.example.jph.Model

import android.util.Log
import com.example.jph.network.NetworkService
import com.example.jph.POJOs.Album
import com.example.jph.POJOs.AlbumAdapter
import com.example.jph.POJOs.Photo
import com.example.jph.Saveable
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class AlbumList(
    val state: Saveable,
    val albums: ArrayList<Pair<Album, Photo?>>,
    val adapter: AlbumAdapter,
    val id: Int
) {

    init {
        NetworkService.getJSONApi().getAlbumsOfUser(id).enqueue(object : Callback,
            retrofit2.Callback<List<Album>> {
            override fun onFailure(call: Call<List<Album>>, t: Throwable) {
                Log.v("retrofit", "getAlbumsOfUser call failed")
                state.retrieve()
                adapter.notifyDataSetChanged()
            }

            override fun onResponse(call: Call<List<Album>>, response: Response<List<Album>>) {
                albums.clear()
                for (album in response.body() ?: return) {
                    albums.add(Pair(album, null))
                }
                for (i in 0 until albums.size) {
                    NetworkService.getJSONApi().getPhotosOfAlbum(albums[i].first.id)
                        .enqueue(object : Callback,
                            retrofit2.Callback<List<Photo>> {
                            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                                Log.v("retrofit", "getPhotosOfAlbum call failed")
                                state.retrieve()
                            }

                            override fun onResponse(
                                call: Call<List<Photo>>,
                                response: Response<List<Photo>>
                            ) {
                                albums[i] = Pair(albums[i].first, (response.body() ?: return)[0])
                                state.save()
                                adapter.notifyDataSetChanged()
                            }
                        })
                    state.save()
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }
}