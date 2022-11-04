package com.example.lostdog.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lostdog.R
import com.example.lostdog.ui.fragments.EnterPhoneNumberFragment
import com.example.lostdog.databinding.ActivityRegisterBinding
import com.example.lostdog.utilities.initFirebase
import com.example.lostdog.utilities.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()

        mToolbar = mBinding.registerToolbar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register)
        replaceFragment(EnterPhoneNumberFragment())
    }
}