package com.example.lostdog.ui.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.example.lostdog.R
import com.example.lostdog.models.PostModel
import com.example.lostdog.utilities.*
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_single_post.*
import kotlinx.android.synthetic.main.toolbar_info.view.*

class SinglePostFragment(private val model: PostModel) : BaseFragment(R.layout.fragment_single_post) {

    private lateinit var mListenerInfoToolbar: AppValueEventListener
    lateinit var mPost: PostModel
    private lateinit var mToolbarInfo: View
    private lateinit var mRefPost: DatabaseReference

    override fun onResume() {
        super.onResume()

        mToolbarInfo = APP_ACTIVITY.mToolbar.toolbar_info
        mToolbarInfo.visibility = View.VISIBLE

        mListenerInfoToolbar = AppValueEventListener {
            mPost = it.getValue(PostModel::class.java) ?: PostModel()
            initInfoToolbar()
            initFields()
            initSettings()
        }

        mRefPost = REF_DATABASE_ROOT.child(NODE_POSTS).child(model.id)
        mRefPost.addValueEventListener(mListenerInfoToolbar)

    }

    private fun initSettings() {
        if (CURRENT_UID == mPost.author)
            setHasOptionsMenu(true)
    }

    private fun initFields() {
        single_post_photo.downloadAndSetImage(mPost.photo_url)
        single_post_description.text = mPost.description
    }

    private fun initInfoToolbar() {
        mToolbarInfo.toolbar_info_title.text = mPost.title
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.single_post_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.single_post_menu_edit -> replaceFragment(EditSinglePostFragment(mPost))
            R.id.single_post_menu_delete -> deletePost(mPost.id) {
                showToast(getString(R.string.single_post_delete))
                replaceFragment(HomeFragment())
            }
        }

        return true
    }

    override fun onPause() {
        super.onPause()

        mToolbarInfo.visibility = View.GONE

        mRefPost.removeEventListener(mListenerInfoToolbar)
    }

}
