package com.example.lostdog.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.utilities.AUTH
import com.example.lostdog.utilities.USER
import com.example.lostdog.utilities.replaceActivity
import com.example.lostdog.utilities.replaceFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        initFields()
    }

    private fun initFields() {
        profile_user_bio.text = USER.bio
        profile_user_phone_number.text = USER.phone
        profile_user_username.text = USER.username
        profile_user_full_name.text = USER.full_name

        profile_button_edit_username.setOnClickListener { replaceFragment(EditUsernameFragment()) }
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