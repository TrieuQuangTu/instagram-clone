package com.example.instagram_clone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.Model.ReelModel
import com.example.instagram_clone.R
import com.example.instagram_clone.Utils.REEL
import com.example.instagram_clone.adapter.ReelAdapter
import com.example.instagram_clone.databinding.FragmentReelBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelFragment : Fragment() {
    private lateinit var binding:FragmentReelBinding
    lateinit var adapter:ReelAdapter
    var reelList =ArrayList<ReelModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReelBinding.inflate(inflater,container,false)
        adapter =ReelAdapter(requireContext(),reelList)
        binding.viewpager.adapter =adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList =ArrayList<ReelModel>()
            reelList.clear()

            for (i in it.documents){
                var reel =i.toObject<ReelModel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }


}