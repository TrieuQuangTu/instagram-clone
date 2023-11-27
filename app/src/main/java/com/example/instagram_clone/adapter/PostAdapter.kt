package com.example.instagram_clone.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.R
import com.example.instagram_clone.Utils.USER_NODE
import com.example.instagram_clone.databinding.PostRvBinding
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostAdapter(var context:Context,var postList:ArrayList<PostModel>):RecyclerView.Adapter<PostAdapter.MyHolder>() {

    inner class MyHolder(val binding:PostRvBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        var binding =PostRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        try {
            Firebase.firestore.collection(USER_NODE).document(postList.get(position).postUrl).get().addOnSuccessListener {
                var user =it.toObject<User>()
                Glide.with(context).load(user!!.image).placeholder(R.drawable.user).into(holder.binding.profileImage)
                holder.binding.profileName.text =user.name
            }

        }catch (e:Exception){

        }
        Glide.with(context).load(postList.get(position).postUrl).placeholder(R.drawable.loading).into(holder.binding.postImage)
        try {
            var text: String = TimeAgo.using(postList.get(position).time.toLong())

            holder.binding.time.text =text
        }catch (e:Exception){
            holder.binding.time.text =""

        }
        holder.binding.itemSend.setOnClickListener {
            var i = Intent(Intent.ACTION_SEND)
            i.type ="text/plain"
            i.putExtra(Intent.EXTRA_TEXT,postList.get(position).postUrl)
            context.startActivity(i)
        }

        holder.binding.caption.text =postList.get(position).caption
        holder.binding.itemLike.setOnClickListener {
            holder.binding.itemLike.setImageResource(R.drawable.heart)
        }

    }
}