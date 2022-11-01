package com.example.lostdog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.databinding.ActivityMainBinding
import com.example.lostdog.ui.fragments.HomeFragment
import com.example.lostdog.ui.objects.AppDrawer
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.replaceActivity
import com.example.lostdog.utilities.replaceFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
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
        if (AUTH.currentUser == null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()
            replaceFragment(HomeFragment())
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        AUTH = FirebaseAuth.getInstance()
        AUTH.setLanguageCode("ru")
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
    }
}