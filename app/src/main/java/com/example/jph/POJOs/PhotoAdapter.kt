package com.example.jph.POJOs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.jph.R
import com.squareup.picasso.Picasso

class PhotoAdapter(private val context: Context, private val dataSource: List<Photo>) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_pic_with_text, parent, false)
        val photoName = rowView.findViewById(R.id.photoName) as TextView
        val photoCover = rowView.findViewById(R.id.photoCover) as ImageView

        val photo = getItem(position) as Photo
        photoName.text = photo.title
        Picasso.get().load(photo.url).placeholder((R.mipmap.ic_launcher)).into(photoCover)
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
