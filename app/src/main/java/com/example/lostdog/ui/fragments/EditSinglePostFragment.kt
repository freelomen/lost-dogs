package com.example.lostdog.ui.fragments

import android.app.Activity
import android.content.Intent
import com.example.lostdog.R
import com.example.lostdog.models.PostModel
import com.example.lostdog.utilities.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_edit_single_post.*

class EditSinglePostFragment(private val mPost: PostModel) :
    BaseEditFragment(R.layout.fragment_edit_single_post) {

    private lateinit var postKey: String

    override fun onResume() {
        super.onResume()

        APP_ACTIVITY.title = getString(R.string.edit_single_post_fragment_title)
        initFields()
    }

    private fun initFields() {
        postKey = mPost.id

        edit_single_post_input_title.setText(mPost.title)
        edit_single_post_input_description.setText(mPost.description)
        edit_single_post_photo.downloadAndSetImage(mPost.photo_url)

        edit_single_post_photo_add.setOnClickListener { editPhoto() }
    }

    private fun editPhoto() {
        CropImage.activity().setAspectRatio(1, 1).setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL).start(APP_ACTIVITY, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_POST_IMAGE).child(postKey)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToPost(it, postKey) {
                        edit_single_post_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.edit_single_post_fragment_photo_update))
                    }
                }
            }
        }
    }

    override fun change() {
        super.change()

        val title = edit_single_post_input_title.text.toString()

        if (title.isEmpty())
            showToast(getString(R.string.new_post_input_title_empty))
        else {
            val description = edit_single_post_input_description.text.toString()

            if (description.isEmpty())
                showToast(getString(R.string.new_post_input_description_empty))
            else {
                val mapPost = hashMapOf<String, Any>()

                mapPost[CHILD_TITLE] = title
                mapPost[CHILD_DESCRIPTION] = description

                REF_DATABASE_ROOT.child(NODE_POSTS).child(postKey).updateChildren(mapPost)
                    .addOnSuccessListener {
                        showToast(getString(R.string.edit_single_post_fragment_post_update))

                        mPost.title = title
                        mPost.description = description

                        fragmentManager?.popBackStack()
                    }
                    .addOnFailureListener { showToast(it.message.toString()) }
            }
        }
    }

}