package com.example.lostdog.ui.fragments

import com.example.lostdog.R
import com.example.lostdog.utilities.*
import kotlinx.android.synthetic.main.fragment_edit_name.*

class EditNameFragment : BaseEditFragment(R.layout.fragment_edit_name) {

    override fun onResume() {
        super.onResume()

        initFullNameList()
    }

    private fun initFullNameList() {
        val fullNameList = USER.full_name.split(" ")

        if (fullNameList.size > 1) {
            profile_input_name.setText(fullNameList[0])
            profile_input_surname.setText(fullNameList[1])
        } else
            profile_input_name.setText(fullNameList[0])
    }

    override fun change() {
        val name = profile_input_name.text.toString()
        val surname = profile_input_surname.text.toString()

        if (name.isEmpty())
            showToast(getString(R.string.profile_toast_name_is_empty))
        else {
            val fullName = "$name $surname"

            REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_FULL_NAME).setValue(fullName)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showToast(getString(R.string.toast_data_update))
                        USER.full_name = fullName
                        APP_ACTIVITY.mAppDrawer.updateHeader()

                        fragmentManager?.popBackStack()
                    }
                }
        }
    }

}