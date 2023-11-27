package com.example.instagram_clone.Post

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram_clone.Model.PostModel
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.Screen.HomeActivity
import com.example.instagram_clone.Utils.POST
import com.example.instagram_clone.Utils.POST_FOLDER
import com.example.instagram_clone.Utils.USER_NODE
import com.example.instagram_clone.Utils.uploadImage
import com.example.instagram_clone.databinding.ActivityPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

class PostActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }
    private var imageUrl: String? = null

    //nhan video sau khi chon tu galley
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) { url ->
                if (url != null) {
                    binding.selectImage.setImageURI(uri)
                    imageUrl = url
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.materialToolbar)
        //display button back in toolbar
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)

        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

        //click Button cancel
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
            finish()
        }

        //click Post
        binding.postButton.setOnClickListener {
            Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid).get().addOnSuccessListener {
                var user = it.toObject<User>()!!

                val post: PostModel = PostModel(
                    postUrl = imageUrl!!,
                    caption = binding.txtCaption.editText?.text.toString(),
                    name = user.name.toString(),
                    time = System.currentTimeMillis().toString()
                )
                Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                    Firebase.firestore.collection(Firebase.auth.currentUser!!.uid).document()
                        .set(post)
                        .addOnSuccessListener {
                            Toast.makeText(this@PostActivity, "Post Successfully", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(this@PostActivity, HomeActivity::class.java))
                            finish()
                        }
                }

            }
        }
    }
}