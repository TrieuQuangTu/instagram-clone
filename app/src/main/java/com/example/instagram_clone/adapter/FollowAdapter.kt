package com.example.instagram_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.R
import com.example.instagram_clone.databinding.FollowRvBinding

class FollowAdapter(var context: Context, var followList:ArrayList<User>):RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(val binding:FollowRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = FollowRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentFollow =followList[position]
        Glide.with(context).load(currentFollow.image)
            .placeholder(R.drawable.user)
            .into(holder.binding.itemFollowImg)

        holder.binding.itemFollowName.text =currentFollow.name
    }
}