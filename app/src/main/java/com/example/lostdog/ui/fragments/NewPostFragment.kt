package com.example.lostdog.ui.fragments

import android.app.Activity
import android.content.Intent
import com.example.lostdog.R
import com.example.lostdog.utilities.*
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_new_post.*

class NewPostFragment : BaseEditFragment(R.layout.fragment_new_post) {
    private val postKey = REF_DATABASE_ROOT.child(NODE_POSTS).push().key.toString()

    override fun onResume() {
        super.onResume()

        APP_ACTIVITY.title = getString(R.string.new_post_fragment_title)
        initFields()
    }

    private fun initFields() {
        new_post_photo_add.setOnClickListener { addPhoto() }
    }

    private fun addPhoto() {
        CropImage.activity().setAspectRatio(1, 1).setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL).start(APP_ACTIVITY, this)
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
                addNewPost(title, description, postKey) {
                    showToast(getString(R.string.new_post_add))
                    fragmentManager?.popBackStack()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_POST_IMAGE).child(postKey)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToPost(it, postKey) {
                        new_post_photo.downloadAndSetImage(it)
                        showToast(getString(R.string.new_post_photo_add))
                    }
                }
            }
        }
    }

}