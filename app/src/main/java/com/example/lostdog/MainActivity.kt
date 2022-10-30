package com.example.lostdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.replaceActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()

        main_button_out.setOnClickListener {
            AUTH.signOut()
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {

        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        AUTH = FirebaseAuth.getInstance()
        AUTH.setLanguageCode("ru")
    }
}