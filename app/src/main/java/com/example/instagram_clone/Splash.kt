package com.example.instagram_clone

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.instagram_clone.Screen.HomeActivity
import com.example.instagram_clone.Screen.SignUpActivity
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        window.statusBarColor = Color.TRANSPARENT

        Handler(Looper.myLooper()!!).postDelayed({
            if (FirebaseAuth.getInstance().currentUser ==null){
                startActivity(Intent(this, SignUpActivity::class.java))
            }else{
                startActivity(Intent(this, HomeActivity::class.java))
            }
            finish()
        },3000)
    }
}