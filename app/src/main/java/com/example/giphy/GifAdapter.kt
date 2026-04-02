package com.example.giphy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class GifAdapter(private var gifs: List<GifObject>) :
    RecyclerView.Adapter<GifAdapter.GifViewHolder>() {

    inner class GifViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.gifImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gif, parent, false)
        return GifViewHolder(view)
    }

    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        val gifUrl = gifs[position].images.fixed_height.url
        holder.imageView.load(gifUrl) {
        }
    }

    override fun getItemCount() = gifs.size

    fun updateGifs(newGifs: List<GifObject>) {
        gifs = newGifs
        notifyDataSetChanged()
    }
}
