package com.example.lostdog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.databinding.ActivityMainBinding
import com.example.lostdog.models.User
import com.example.lostdog.ui.fragments.HomeFragment
import com.example.lostdog.ui.objects.AppDrawer
import com.example.lostdog.utilities.*
import com.theartofdev.edmodo.cropper.CropImage

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        APP_ACTIVITY = this

        initFirebase()
        initUser{
            initFields()
            initFunc()
        }
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolbar)
            mAppDrawer.create()

            replaceFragment(HomeFragment(), false)
        } else
            replaceActivity(RegisterActivity())
    }

    private fun initFields() {
        mToolbar = mBinding.mainToolbar
        mAppDrawer = AppDrawer(this, mToolbar)
    }

    override fun onStart() {
        super.onStart()

        AppStates.updateStates(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()

        AppStates.updateStates(AppStates.OFFLINE)
    }
}
