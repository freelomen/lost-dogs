package com.example.lostdog.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.lostdog.MainActivity
import com.example.lostdog.R
import com.example.lostdog.utilities.*
import kotlinx.android.synthetic.main.fragment_edit_name.*

class EditNameFragment : Fragment(R.layout.fragment_edit_name) {

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        (activity as MainActivity).menuInflater.inflate(R.menu.profile_action_menu_confirm, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile_confirm_change -> changeName()
        }
        return true
    }

    private fun changeName() {
        val name = profile_input_name.text.toString()
        val surame = profile_input_surname.text.toString()

        if (name.isEmpty()) {
            showToast("Имя не может быть пустым")
        } else {
            val fullName = "$name $surame"

            REF_DATABASE_ROOT.child(NODE_USERS).child(UID).child(CHILD_FULL_NAME).setValue(fullName).addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast("Данные добавлены")
                    USER.full_name = fullName
                    replaceFragment(ProfileFragment())
                }
            }
        }
    }

}