package com.example.lostdog.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.utilities.*
import kotlinx.android.synthetic.main.fragment_edit_name.*
import kotlinx.android.synthetic.main.fragment_edit_username.*
import java.util.*

class EditUsernameFragment : BaseFragment(R.layout.fragment_edit_username) {

    lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        profile_input_username.setText(USER.username)
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

    private fun change() {
        mNewUsername = profile_input_username.text.toString().toLowerCase(Locale.getDefault())

        if (mNewUsername.isEmpty()) {
            showToast("Имя пользователя не может быть пустым")
        } else {
            REF_DATABASE_ROOT.child(NODE_USERNAMES)
                .addListenerForSingleValueEvent(AppValueEventListener {
                    if (it.hasChild(mNewUsername))
                        showToast("Имя пользователя уже занято")
                    else
                        changeUsername()
                })
        }
    }

    private fun changeUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(UID)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    updateCurrentUsername()
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_USERNAME).setValue(mNewUsername)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    deleteOldUsername()
                else
                    showToast(it.exception?.message.toString())
            }
    }

    private fun deleteOldUsername() {
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(USER.username).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Данные добавлены")
                    USER.username = mNewUsername
                    replaceFragment(ProfileFragment())
                } else
                    showToast(it.exception?.message.toString())
            }
    }

}