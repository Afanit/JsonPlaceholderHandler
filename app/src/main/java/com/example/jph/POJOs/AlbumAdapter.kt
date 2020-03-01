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

class AlbumAdapter(
    private val context: Context,
    private val dataSource: List<Pair<Album, Photo?>>
) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.list_item_pic_with_text, parent, false)
        val albumName = rowView.findViewById(R.id.photoName) as TextView
        val albumCover = rowView.findViewById(R.id.photoCover) as ImageView
        val album = getItem(position) as Pair<Album, Photo>
        albumName.text = album.first.title
        Picasso.get().load(dataSource[position].second?.url).placeholder((R.mipmap.ic_launcher))
            .into(albumCover)
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