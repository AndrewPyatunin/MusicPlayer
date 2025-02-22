package com.andreich.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.facebook.shimmer.ShimmerFrameLayout

object DiffCallback : DiffUtil.ItemCallback<MusicItem>() {

    override fun areItemsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
        return oldItem == newItem
    }
}

class BaseMusicListAdapter :
    ListAdapter<MusicItem, BaseMusicListAdapter.MovieViewHolder>(DiffCallback) {

    var onMusicClick: OnMusicTrackClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.music_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val musicItem = getItem(position)
        musicItem?.let {
            with(holder) {
                musicTitle.text = musicItem.title
                author.text = musicItem.artist
                musicImage.visibility = View.GONE
                shimmerItem.visibility = View.VISIBLE
                if (musicItem.cover != null) {
                    Glide.with(holder.itemView.context).load(musicItem.cover)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                shimmerItem.visibility = View.GONE
                                musicImage.visibility = View.VISIBLE
                                musicImage.setImageDrawable(resource)
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                                shimmerItem.visibility = View.GONE
                                musicImage.visibility = View.VISIBLE
                                musicImage.setImageDrawable(
                                    ContextCompat.getDrawable(
                                        holder.itemView.context,
                                        R.drawable.music_default
                                    )
                                )
                            }
                        })
                } else {
                    shimmerItem.visibility = View.GONE
                    musicImage.visibility = View.VISIBLE
                    musicImage.setImageDrawable(
                        ContextCompat.getDrawable(
                            holder.itemView.context,
                            R.drawable.music_default
                        )
                    )
                }

                itemView.setOnClickListener {
                    onMusicClick?.onMusicClick(musicItem)
                }
            }
        }

    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shimmerItem = itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer_music_item)
        val musicImage = itemView.findViewById<ImageView>(R.id.music_image)
        val musicTitle = itemView.findViewById<TextView>(R.id.music_title)
        val author = itemView.findViewById<TextView>(R.id.author_name)
    }

    interface OnMusicTrackClickListener {

        fun onMusicClick(musicItem: MusicItem)
    }
}
