package com.example.jph


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jph.Model.UserList
import com.example.jph.POJOs.User
import com.example.jph.POJOs.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_albums.*
import kotlinx.android.synthetic.main.activity_photos.*

class MainActivity : AppCompatActivity(), Saveable {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = UserAdapter(this, users)
        UserList(this, users, adapter)
        userListView.adapter = adapter
    }

    private val users: ArrayList<User> = arrayListOf()
    override fun save() {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val gson = Gson()
        val json = gson.toJson(users)
        editor.putString("USERS", json)
        editor.apply()
    }

    override fun retrieve() {
        val prefs = getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE)
        val gson = Gson()
        val json = prefs.getString("USERS", null)
        val listType = object : TypeToken<ArrayList<User>>() {
        }.type
        if (gson.fromJson<ArrayList<User>>(json, listType) != null) {
            users.clear()
            users.addAll(gson.fromJson(json, listType))
        }
        else
        {
            val toast = Toast.makeText(this,"Can't load this page. Internet connection required",Toast.LENGTH_SHORT)
            toast.show()
        }
    }
}
