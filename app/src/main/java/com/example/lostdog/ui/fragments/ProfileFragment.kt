package com.example.lostdog.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.replaceActivity
import com.example.lostdog.utilities.replaceFragment

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.profile_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_menu_exit_username -> {
                AUTH.signOut()
                (activity as MainActivity).replaceActivity(RegisterActivity())
            }
            R.id.profile_menu_edit_username -> replaceFragment(EditNameFragment())
        }
    return true
    }

}