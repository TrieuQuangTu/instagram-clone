package com.example.instagram_clone.Screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.instagram_clone.Model.User
import com.example.instagram_clone.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txtDonthave.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            finish()
        }

        binding.ButtonLogin.setOnClickListener {
            if (binding.loginEmail.editText?.text.toString().equals("") or
                binding.loginPassword.editText?.text.toString().equals("")
            ) {
                Toast.makeText(this@LoginActivity, "Please fill information", Toast.LENGTH_SHORT)
                    .show()
            } else {
                var user = User(
                    binding.loginEmail.editText?.text.toString(),
                    binding.loginPassword.editText?.text.toString()
                )

                Firebase.auth.signInWithEmailAndPassword(user.email!!,user.password!!)
                    .addOnCompleteListener {
                        if (it.isSuccessful){
                            Toast.makeText(this@LoginActivity,"Sign In successfully",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                            finish()
                        }else{
                            Toast.makeText(this@LoginActivity,"Sign In Failed: ${it.exception}",Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }
    }
}