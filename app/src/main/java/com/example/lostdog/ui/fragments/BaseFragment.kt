package com.example.lostdog.ui.fragments

import androidx.fragment.app.Fragment
import com.example.lostdog.utilities.APP_ACTIVITY
import com.example.lostdog.utilities.hiddenKeyboard

open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()

        hiddenKeyboard()
        APP_ACTIVITY.mAppDrawer.disableDrawer()
    }

    override fun onStop() {
        super.onStop()

        APP_ACTIVITY.mAppDrawer.enableDrawer()
    }

}