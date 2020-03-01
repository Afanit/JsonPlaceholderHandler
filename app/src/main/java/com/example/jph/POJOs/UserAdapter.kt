package com.example.jph.POJOs

import android.Manifest
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.jph.R
import android.content.Intent
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.jph.AlbumsActivity


class UserAdapter(private val context: Context, private val dataSource: List<User>) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_user, parent, false)
        val userName = rowView.findViewById(R.id.userName) as TextView
        val userPhone = rowView.findViewById(R.id.userPhone) as TextView
        val userEmail = rowView.findViewById(R.id.userEmail) as TextView
        val userSite = rowView.findViewById(R.id.userSite) as TextView
        val user = getItem(position) as User
        userName.text = user.name
        userName.setOnClickListener {
            val intent = Intent(context, AlbumsActivity::class.java)
            intent.putExtra("userId", user.id)
            intent.putExtra("userName", user.name)
            context.startActivity(intent)
        }
        userPhone.text = "\uD83D\uDCDE" + user.phone
        userPhone.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:" + user.phone)
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            } else {
                context.startActivity(intent)
            }
        }
        userEmail.text = "\uD83D\uDCE7" + user.email
        userEmail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = (Uri.parse("mailto:"))
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(user.email))
            context.startActivity(Intent.createChooser(intent, "Choose Email Client..."))
        }
        userSite.text = "\uD83C\uDF10" + user.website
        userSite.setOnClickListener {
            var url = user.website.toString()
            if (!url.startsWith("http://") && !url.startsWith("https://")) url = "http://$url"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return dataSource.size
    }

}