package com.example.instagram_clone.Post

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.Model.ReelModel
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.Screen.HomeActivity
import com.example.instagram_clone.Utils.POST_Video
import com.example.instagram_clone.Utils.REEL
import com.example.instagram_clone.Utils.USER_NODE
import com.example.instagram_clone.Utils.uploadReel
import com.example.instagram_clone.databinding.ActivityPostBinding
import com.example.instagram_clone.databinding.ActivityReelBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class ReelActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityReelBinding.inflate(layoutInflater)
    }
    lateinit var progressDialog:ProgressDialog

    private lateinit var videoUrl: String
    private val launcher =registerForActivityResult(ActivityResultContracts.GetContent()){uri->
        uri?.let {
            uploadReel(uri, POST_Video,progressDialog) { url->
                if (url !=null){
                    videoUrl =url
                }
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)

        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.reelCancel.setOnClickListener {
            startActivity(Intent(this@ReelActivity,HomeActivity::class.java))
            finish()
        }

        binding.reelPost.setOnClickListener {

            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {
                    var user:User =it.toObject<User>()!!

                    val reel =ReelModel(videoUrl,binding.txtCaption.editText?.text.toString(),user.image!!)
                    Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                        Toast.makeText(this@ReelActivity,"Post Reel Successfullly",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ReelActivity,HomeActivity::class.java))
                        finish()
                    }
                }

        }
    }
}