package com.example.lostdog.ui.fragments

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lostdog.R
import com.example.lostdog.models.CommonModel
import com.example.lostdog.models.PostModel
import com.example.lostdog.utilities.*
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_user_post.*
import kotlinx.android.synthetic.main.post_item.view.*

class UserPostFragment : BaseFragment(R.layout.fragment_user_post) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<CommonModel, UserPostsHolder>
    private lateinit var mRefUserPosts: DatabaseReference
    private lateinit var mRefPosts: DatabaseReference

    override fun onResume() {
        super.onResume()

        APP_ACTIVITY.title = getString(R.string.user_post_fragment_title)
        setHasOptionsMenu(true)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = user_post_recycler_view
        mRefUserPosts = REF_DATABASE_ROOT.child(NODE_POSTS_USERS).child(CURRENT_UID)

        val options = FirebaseRecyclerOptions.Builder<CommonModel>()
            .setQuery(mRefUserPosts, CommonModel::class.java).build()

        mAdapter = object : FirebaseRecyclerAdapter<CommonModel, UserPostsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPostsHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)

                return UserPostsHolder(view)
            }

            override fun onBindViewHolder(
                holder: UserPostsHolder,
                position: Int,
                model: CommonModel
            ) {
                mRefPosts = REF_DATABASE_ROOT.child(NODE_POSTS).child(model.id)

                mRefPosts.addValueEventListener(AppValueEventListener {
                    val post = it.getValue(PostModel::class.java) ?: PostModel()

                    holder.title.text = post.title
                    holder.photo.downloadAndSetImage(post.photo_url)
                })
            }
        }

        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        APP_ACTIVITY.menuInflater.inflate(R.menu.user_post_menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_post_add -> replaceFragment(NewPostFragment())
        }

        return true
    }

    class UserPostsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.post_title
        val photo: CircleImageView = view.post_photo
    }

    override fun onPause() {
        super.onPause()

        mAdapter.stopListening()
    }

}
