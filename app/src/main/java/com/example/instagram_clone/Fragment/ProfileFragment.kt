package com.example.instagram_clone.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.Screen.SignUpActivity
import com.example.instagram_clone.adapter.ViewPagerAdapter
import com.example.instagram_clone.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater,container,false)

        binding.profileEdtProfile.setOnClickListener {
            val intent =Intent(activity,SignUpActivity::class.java)
            intent.putExtra("MODE",1)
            activity?.startActivity(intent)
            activity?.finish()

        }

        //do viewpager vao adapter
        viewPagerAdapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        viewPagerAdapter.addFragments(MyPostFragment(),"My Post")
        viewPagerAdapter.addFragments(MyReelsFragment(),"My Reel")

        binding.pager.adapter =viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.pager)


        return binding.root
    }

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()
        val userId =auth.currentUser!!.uid

        Firebase.firestore.collection("User").document(userId).get()
            .addOnSuccessListener {
                val user: User =it.toObject<User>()!!
                binding.profileName.text =user.name
                binding.profileBio.text =user.email

                //kiem tra profile Image khac null hoac ko rong
                if (!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profileImage)
                }
            }
    }
}