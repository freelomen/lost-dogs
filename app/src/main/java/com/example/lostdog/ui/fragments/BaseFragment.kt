package com.example.lostdog.ui.fragments

import androidx.fragment.app.Fragment
import com.example.lostdog.utilities.hiddenKeyboard

open class BaseFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()
        hiddenKeyboard()
    }

}