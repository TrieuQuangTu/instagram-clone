package com.example.instagram_clone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.R
import com.example.instagram_clone.Utils.FOLLOW
import com.example.instagram_clone.databinding.SearchRvBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchAdapter(
    var context: Context,
    var userList:ArrayList<User>
) :RecyclerView.Adapter<SearchAdapter.MyViewHolder>(){

    inner class MyViewHolder(var binding:SearchRvBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var binding = SearchRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var isFollow =false
        var currentItem =userList[position]
        Glide.with(context).load(currentItem.name)
            .placeholder(R.drawable.user).into(holder.binding.searchItemProfile)

        holder.binding.searchItemName.text =currentItem.name
        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
            .whereEqualTo("email",currentItem.email)
            .get().addOnSuccessListener {
                if (it.documents.size ==0){
                    isFollow=false


                }else{
                    holder.binding.follow.text ="Unfollow"
                    isFollow=true
                }
            }
        holder.binding.follow.setOnClickListener {
            if (isFollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
                    .whereEqualTo("email",currentItem.email)
                    .get().addOnSuccessListener {
                      Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
                          .document(it.documents.get(0).id).delete()
                        holder.binding.follow.text ="follow"
                        isFollow=false
                    }

            }else{
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid + FOLLOW)
                    .document().set(currentItem)
                holder.binding.follow.text ="Unfollow"
                isFollow=true
            }
        }
    }
}