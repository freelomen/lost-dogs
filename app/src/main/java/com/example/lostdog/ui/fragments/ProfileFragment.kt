package com.example.lostdog.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import com.example.lostdog.R

class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.profile_action_menu, menu)
    }

}