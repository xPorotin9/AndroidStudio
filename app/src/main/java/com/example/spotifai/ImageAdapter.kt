package com.example.spotifai

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView

class ImageAdapter(private val context: Context, private val images: Array<Int>) : BaseAdapter() {

    override fun getCount(): Int = images.size

    override fun getItem(position: Int): Any = images[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView: ImageView = convertView?.findViewById(R.id.imageView) ?: run {
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.grid_item_image, parent, false)
            view.findViewById(R.id.imageView)
        }
        imageView.setImageResource(images[position])
        return imageView
    }
}
