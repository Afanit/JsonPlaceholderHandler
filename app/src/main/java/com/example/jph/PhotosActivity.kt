package com.example.jph

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jph.Model.PhotoList
import com.example.jph.POJOs.Photo
import com.example.jph.POJOs.PhotoAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_photos.*

class PhotosActivity : AppCompatActivity(), Saveable {

    override fun save() {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(photos)
        editor.putString("PHOTOS_OF_${intent.getIntExtra("albumId", 1)}}", json)
        editor.apply()
    }

    override fun retrieve() {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("PHOTOS_OF_${intent.getIntExtra("albumId", 1)}}", null)
        val listType = object : TypeToken<ArrayList<Photo>>() {
        }.type
        if (gson.fromJson<ArrayList<Photo>>(json, listType) != null) {
            photos.clear()
            photos.addAll(gson.fromJson(json, listType))
        }
        else
        {
            val toast = Toast.makeText(this,"Can't load this page. Internet connection required",Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)
        val adapter = PhotoAdapter(this, photos)
        photoListView.adapter = adapter
        PhotoList(this, photos, adapter, intent.getIntExtra("albumId", 1))
        val title = "Photos of \"" +intent.getStringExtra("albumTitle") +"\""
        photosViewTitle.text = title
    }

    val photos = arrayListOf<Photo>()
}
