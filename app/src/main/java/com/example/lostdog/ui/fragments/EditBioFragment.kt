package com.example.lostdog.ui.fragments

import com.example.lostdog.R
import com.example.lostdog.utilities.*
import kotlinx.android.synthetic.main.fragment_edit_bio.*

class EditBioFragment : BaseEditFragment(R.layout.fragment_edit_bio) {

    override fun onResume() {
        super.onResume()

        profile_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()

        val bio = profile_input_bio.text.toString()
        REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_BIO).setValue(bio)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    showToast(getString(R.string.toast_data_update))
                    USER.bio = bio
                    fragmentManager?.popBackStack()
                }
            }
    }

}