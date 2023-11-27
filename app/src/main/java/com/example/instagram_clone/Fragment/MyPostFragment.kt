package com.example.instagram_clone.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.R
import com.example.instagram_clone.adapter.MyPostRvAdapter
import com.example.instagram_clone.databinding.FragmentMyPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class MyPostFragment : Fragment() {
    private lateinit var binding:FragmentMyPostBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyPostBinding.inflate(inflater,container,false)

        var postList =ArrayList<PostModel>()
        var adapter =MyPostRvAdapter(requireContext(),postList)
        binding.postRecyclerview.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.postRecyclerview.adapter =adapter
        Firebase.firestore.collection("Post")
            .get().addOnSuccessListener {
                var tempList = arrayListOf<PostModel>()
                for (i in it.documents){
                    var post:PostModel =i.toObject<PostModel>()!!
                        tempList.add(post)
                }
                postList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }


        //c2:
        /* for (data in it.documents){
             val post:PostModel? =data.toObject(PostModel::class.java)
             postList.add(post!!)
         }
         binding.postRecyclerview.adapter=adapter
         adapter.notifyDataSetChanged()*/
        return binding.root
    }

}