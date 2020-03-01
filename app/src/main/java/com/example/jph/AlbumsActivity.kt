package com.example.jph

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jph.Model.AlbumList
import com.example.jph.POJOs.Album
import com.example.jph.POJOs.AlbumAdapter
import com.example.jph.POJOs.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_albums.*

class AlbumsActivity : AppCompatActivity(), Saveable {
    override fun save() {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(albums)
        editor.putString("ALBUMS_OF_${intent.getIntExtra("userId", 1)}}", json)
        editor.apply()
    }

    override fun retrieve() {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("ALBUMS_OF_${intent.getIntExtra("userId", 1)}}", null)
        val listType = object : TypeToken<ArrayList<Pair<Album, Photo>>>() {
        }.type
        if (gson.fromJson<ArrayList<Pair<Album, Photo>>>(json, listType) != null) {
            albums.clear()
            albums.addAll(gson.fromJson(json, listType))
        }
        else
        {
            val toast = Toast.makeText(this,"Can't load this page. Internet connection required",Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_albums)
        val adapter = AlbumAdapter(this, albums)
        albumListView.adapter = adapter
        AlbumList(this, albums, adapter, intent.getIntExtra("userId", 1))
        albumListView.setOnItemClickListener { parent, view, position, id ->
            val element = adapter.getItem(position) as Pair<Album, Photo>
            val intent = Intent(this, PhotosActivity::class.java)
            intent.putExtra("albumId", element.first.id)
            intent.putExtra("albumTitle", element.first.title)
            startActivity(intent)
        }
        val title = "Albums of " +intent.getStringExtra("userName")
        albumsViewTitle.text = title
    }

    private val albums = arrayListOf<Pair<Album, Photo?>>()
}
