package com.example.instagram_clone.Screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.Utils.USER_PROFILE_FOLDER
import com.example.instagram_clone.Utils.uploadImage
import com.example.instagram_clone.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.squareup.picasso.Picasso

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding

    lateinit var user:User
    private val launcher =registerForActivityResult(ActivityResultContracts.GetContent()){
        uri->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOLDER){
                if (it ==null){

                }else{
                    user.image =it
                    binding.profileImage.setImageURI(uri)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user =User()

        binding.ButtonSIgnUp.setOnClickListener {

            if (intent.hasExtra("MODE")){
                if (intent.getIntExtra("MODE", -1)==1){
                    Firebase.firestore.collection("User")
                        .document(Firebase.auth.currentUser!!.uid)
                        .set(user)
                        .addOnCompleteListener {
                            startActivity(Intent(this@SignUpActivity,HomeActivity::class.java))
                            finish()
                        }
                }
            }else{

            }
            if (binding.name.editText?.text.toString().equals("") or
                binding.email.editText?.text.toString().equals("") or
                binding.password.editText?.text.toString().equals("")
                )
            {
                Toast.makeText(this@SignUpActivity,"Please fill information",Toast.LENGTH_SHORT).show()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result->
                    if (result.isSuccessful){
                        user.name =binding.name.editText?.text.toString()
                        user.email =binding.email.editText?.text.toString()
                        user.password =binding.password.editText?.text.toString()

                        Firebase.firestore.collection("User")
                            .document(Firebase.auth.currentUser!!.uid)
                            .set(user)
                            .addOnSuccessListener {
                                Toast.makeText(this@SignUpActivity,"Account storage in firebase store successfully",Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
                                finish()
                            }

                        Toast.makeText(this@SignUpActivity,"Create account successfully",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@SignUpActivity,result.exception?.localizedMessage,Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }

        binding.login.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
        }

        //nhan du lieu tu ProfileFragment de update profile
        //doan nay can tim hieu chat gpt va google
        if (intent.hasExtra("MODE")){
            if (intent.getIntExtra("MODE", -1)==1){
                binding.ButtonSIgnUp.text ="Update Profile"

                Firebase.firestore.collection("User").document(Firebase.auth.currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user =it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()){
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.name.editText?.setText(user.name)
                        binding.password.editText?.setText(user.password)
                        binding.email.editText?.setText(user.email)


                    }
            }
        }

    }
}



