package com.example.projeton3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.projeton3.model.UnsplashPhoto
import com.squareup.picasso.Picasso

class UnsplashAdapter(private val photos: List<UnsplashPhoto>) :
    RecyclerView.Adapter<UnsplashAdapter.UnsplashViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnsplashViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return UnsplashViewHolder(view)
    }

    override fun onBindViewHolder(holder: UnsplashViewHolder, position: Int) {
        val photo = photos[position]
        Picasso.get().load(photo.urls.regular).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    class UnsplashViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(android.R.id.icon)
    }
}
