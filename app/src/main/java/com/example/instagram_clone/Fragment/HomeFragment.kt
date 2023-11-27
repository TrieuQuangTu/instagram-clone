package com.example.instagram_clone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.R
import com.example.instagram_clone.Utils.FOLLOW
import com.example.instagram_clone.Utils.POST
import com.example.instagram_clone.adapter.FollowAdapter
import com.example.instagram_clone.adapter.PostAdapter
import com.example.instagram_clone.databinding.FragmentHomeBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject


class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private var postList =ArrayList<PostModel>()
    private lateinit var adapter: PostAdapter

    //display item follow in recyclerview
    private var followList = ArrayList<User>()
    private lateinit var followAdapter: FollowAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        adapter = PostAdapter(requireContext(),postList)
        binding.postRv.layoutManager =LinearLayoutManager(requireContext())
        binding.postRv.adapter =adapter


        //follow item
        binding.followRv.layoutManager =LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        followAdapter =FollowAdapter(requireContext(),followList)
        binding.followRv.adapter =followAdapter

        setHasOptionsMenu(true)

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
            .get().addOnSuccessListener {
                var tempList =ArrayList<User>()
                followList.clear()
                for(i in it.documents){
                    var user =i.toObject<User>()!!
                    tempList.add(user)
                }
                followList.addAll(tempList)
                followAdapter.notifyDataSetChanged()
            }


        Firebase.firestore.collection(POST).get().addOnSuccessListener {
            var tempList =ArrayList<PostModel>()
            postList.clear()
            for (i in it.documents){
                var post =i.toObject<PostModel>()!!
                tempList.add(post)
            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}