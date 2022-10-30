package com.example.lostdog.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lostdog.fragments.EnterPhoneNumberFragment
import com.example.lostdog.databinding.ActivityRegisterBinding
import com.example.lostdog.utilities.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(EnterPhoneNumberFragment())
    }
}