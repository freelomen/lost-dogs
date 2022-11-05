package com.example.lostdog

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.databinding.ActivityMainBinding
import com.example.lostdog.ui.fragments.HomeFragment
import com.example.lostdog.ui.objects.AppDrawer
import com.example.lostdog.utilities.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    lateinit var mAppDrawer: AppDrawer
    lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        APP_ACTIVITY = this

        initFirebase()
        initUser {
            initContacts()
            initFields()
            initFunc()
        }
    }

    private fun initContacts() {
        if (checkPermission(READ_CONTACTS))
            showToast("Разрешение дано")
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
        mAppDrawer = AppDrawer()
    }

    override fun onStart() {
        super.onStart()

        AppStates.updateStates(AppStates.ONLINE)
    }

    override fun onStop() {
        super.onStop()

        AppStates.updateStates(AppStates.OFFLINE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (ContextCompat.checkSelfPermission(
                APP_ACTIVITY,
                READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        )
            initContacts()
    }

}
