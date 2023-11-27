package com.example.instagram_clone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.Model.ReelModel
import com.example.instagram_clone.R
import com.example.instagram_clone.Utils.REEL
import com.example.instagram_clone.adapter.MyPostRvAdapter
import com.example.instagram_clone.adapter.MyReelRvAdapter
import com.example.instagram_clone.databinding.FragmentMyReelsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class MyReelsFragment : Fragment() {
    private lateinit var binding:FragmentMyReelsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyReelsBinding.inflate(inflater,container,false)


        var reelList =ArrayList<ReelModel>()
        var adapter =MyReelRvAdapter(requireContext(),reelList)
        binding.reelRec.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.reelRec.adapter =adapter
        Firebase.firestore.collection("Reel")
            .get().addOnSuccessListener {
                var tempList = arrayListOf<ReelModel>()
                for (i in it.documents){
                    var reel: ReelModel =i.toObject<ReelModel>()!!
                    tempList.add(reel)
                }
                reelList.addAll(tempList)
                adapter.notifyDataSetChanged()
            }

        return binding.root
    }
}