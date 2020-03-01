package com.example.jph.POJOs

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Album {
    @SerializedName("userId")
    @Expose
    var userId: Int = 0
    @SerializedName("id")
    @Expose
    var id: Int = 0
    @SerializedName("title")
    @Expose
    var title: String? = null
}