package com.example.instagram_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.Model.ReelModel
import com.example.instagram_clone.databinding.ItemPostBinding
import com.squareup.picasso.Picasso

class MyReelRvAdapter(
    var context: Context,
    var reelList:ArrayList<ReelModel>
): RecyclerView.Adapter<MyReelRvAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: ItemPostBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemPostBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return reelList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(reelList.get(position).reelUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.postImage)
    }
}