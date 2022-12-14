package com.example.lostdog.ui.fragments

import com.example.lostdog.R
import com.example.lostdog.utilities.*
import kotlinx.android.synthetic.main.fragment_edit_username.*
import java.util.*

class EditUsernameFragment : BaseEditFragment(R.layout.fragment_edit_username) {

    private lateinit var mNewUsername: String

    override fun onResume() {
        super.onResume()

        profile_input_username.setText(USER.username)
    }

    override fun change() {
        mNewUsername = profile_input_username.text.toString().toLowerCase(Locale.getDefault())

        if (mNewUsername.isEmpty())
            showToast("Имя пользователя не может быть пустым")
        else {
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
        REF_DATABASE_ROOT.child(NODE_USERNAMES).child(mNewUsername).setValue(CURRENT_UID)
            .addOnCompleteListener {
                if (it.isSuccessful)
                    updateCurrentUsername()
            }
    }

    private fun updateCurrentUsername() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_USERNAME).setValue(mNewUsername)
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
                    showToast(getString(R.string.toast_data_update))
                    USER.username = mNewUsername
                    fragmentManager?.popBackStack()
                } else
                    showToast(it.exception?.message.toString())
            }
    }

}