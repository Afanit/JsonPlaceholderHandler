package com.example.jph.network

import com.example.jph.POJOs.Album
import com.example.jph.POJOs.Photo
import com.example.jph.POJOs.Post
import com.example.jph.POJOs.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface JSONPlaceHolderApi {
    @GET("/posts/{id}")
    fun getPostWithID(@Path("id") id: Int): Call<Post>

    @GET("/posts")
    fun getAllPosts(): Call<List<Post>>

    @GET("users")
    fun getAllUsers(): Call<List<User>>

    @GET("/albums")
    fun getAlbumsOfUser(@Query("userId") id: Int): Call<List<Album>>

    @GET("/photos")
    fun getPhotosOfAlbum(@Query("albumId") albumId: Int): Call<List<Photo>>
}