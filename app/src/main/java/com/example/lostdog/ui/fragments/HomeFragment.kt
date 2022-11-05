package com.example.lostdog.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.lostdog.R
import com.example.lostdog.models.PostModel
import com.example.lostdog.utilities.APP_ACTIVITY
import com.example.lostdog.utilities.NODE_POSTS
import com.example.lostdog.utilities.REF_DATABASE_ROOT
import com.example.lostdog.utilities.downloadAndSetImage
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.post_item.view.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: FirebaseRecyclerAdapter<PostModel, PostsHolder>
    private lateinit var mRefPosts: DatabaseReference

    override fun onResume() {
        super.onResume()

        APP_ACTIVITY.title = getString(R.string.home_fragment_title)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = post_recycler_view
        mRefPosts = REF_DATABASE_ROOT.child(NODE_POSTS)

        val options = FirebaseRecyclerOptions.Builder<PostModel>()
            .setQuery(mRefPosts, PostModel::class.java).build()

        mAdapter = object : FirebaseRecyclerAdapter<PostModel, PostsHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsHolder {
                val view =
                    LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)

                return PostsHolder(view)
            }

            override fun onBindViewHolder(
                holder: PostsHolder,
                position: Int,
                model: PostModel
            ) {
                holder.title.text = model.title
                holder.photo.downloadAndSetImage(model.photo_url)
            }
        }

        mRecyclerView.adapter = mAdapter
        mAdapter.startListening()
    }

    class PostsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.post_title
        val photo: CircleImageView = view.post_photo
    }

    override fun onPause() {
        super.onPause()

        mAdapter.stopListening()
    }

}