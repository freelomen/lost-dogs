package com.example.lostdog.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.lostdog.R
import com.example.lostdog.activities.RegisterActivity
import com.example.lostdog.utilities.*
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
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
        profile_user_photo.downloadAndSetImage(USER.photo_url)

        profile_button_edit_username.setOnClickListener { replaceFragment(EditUsernameFragment()) }
        profile_button_edit_bio.setOnClickListener { replaceFragment(EditBioFragment()) }
        profile_edit_user_photo.setOnClickListener { editUserPhoto() }
    }

    private fun editUserPhoto() {
        CropImage.activity().setAspectRatio(1, 1).setRequestedSize(600, 600)
            .setCropShape(CropImageView.CropShape.OVAL).start(APP_ACTIVITY, this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.profile_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_menu_exit_username -> {
                AUTH.signOut()
                APP_ACTIVITY.replaceActivity(RegisterActivity())
            }
            R.id.profile_menu_edit_username -> replaceFragment(EditNameFragment())
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = CropImage.getActivityResult(data).uri
            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE).child(CURRENT_UID)

            putImageToStorage(uri, path) {
                getUrlFromStorage(path) {
                    putUrlToDatabase(it) {
                        profile_user_photo.downloadAndSetImage(it)
                        showToast("Данные обновлены")
                        USER.photo_url = it
                        APP_ACTIVITY.mAppDrawer.updateHeader()
                    }
                }
            }
        }
    }

}