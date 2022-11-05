package com.example.lostdog.ui.fragments

import com.example.lostdog.R
import com.example.lostdog.utilities.*
import kotlinx.android.synthetic.main.fragment_new_post.*

class NewPostFragment : BaseEditFragment(R.layout.fragment_new_post) {

    override fun onResume() {
        super.onResume()

        APP_ACTIVITY.title = getString(R.string.new_post_fragment_title)
        initFields()
    }

    private fun initFields() {

    }

    override fun change() {
        super.change()

        val title = new_post_input_title.text.toString()

        if (title.isEmpty())
            showToast(getString(R.string.new_post_input_title_empty))
        else {
            val description = new_post_input_description.text.toString()
            if (description.isEmpty())
                showToast(getString(R.string.new_post_input_description_empty))
            else {
                val postKey = REF_DATABASE_ROOT.child(NODE_POSTS).push().key.toString()

                addNewPost(title, description, postKey) {
                    replaceFragment(NewPostPhotoFragment(postKey))
                }
            }
        }
    }

}