package com.example.lostdog.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.utilities.APP_ACTIVITY
import com.example.lostdog.utilities.hiddenKeyboard

open class BaseEditFragment(layout: Int) : Fragment(layout) {

    override fun onStart() {
        super.onStart()

        setHasOptionsMenu(true)
        hiddenKeyboard()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.profile_action_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_confirm_change -> change()
        }

        return true
    }

    open fun change() {

    }

}