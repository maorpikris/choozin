package com.choozin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.choozin.R
import com.choozin.infra.adapters.PostsAdapter
import com.choozin.infra.base.BaseFragment
import com.choozin.managers.AuthenticationManager
import com.choozin.managers.ProfileManager
import com.choozin.models.PostItem
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*


class ProfileFragment : BaseFragment() {

    private val profileManager: ProfileManager = ProfileManager().instance
    private var postsList: ArrayList<PostItem?> = arrayListOf()
    private lateinit var recyclerViewAdapter: PostsAdapter
    val bundle: Bundle? = this.arguments


    override fun updateUI() {
        // Updating the ui of the top part of the screen.
        username.text = ProfileManager.currentProfileUser.username
        description.text = ProfileManager.currentProfileUser.description
        stars.text = ProfileManager.currentProfileUser.stars.toString()
        Glide.with(activity!!.applicationContext)
            .load(AuthenticationManager.buildGlideUrlWithAuth(ProfileManager.currentProfileUser.profileUrl))
            .apply(RequestOptions.circleCropTransform()).into(profile_image)
        // on update ui clearing the old list and adding the items from the manager list.
        postsList.clear()
        postsList.addAll(ProfileManager().instance.profilePosts)
        initAdapter()
    }

    // Asking the manager to get the posts from the server.
    private fun populateData() {
        ProfileManager().instance
            .getProfilePosts(profileManager.idCurrentProfileUser)

    }

    // initializing the adapter and notifying about the change.
    private fun initAdapter() {
        if (swipeContainer != null) {
            swipeContainer.isRefreshing = false
        }
        if (!this::recyclerViewAdapter.isInitialized) {
            recyclerViewAdapter = PostsAdapter(postsList)
        }
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // initializing all the critic things and updating ui if possible
        if (ProfileManager().instance.profilePosts != null && ProfileManager().instance.profilePosts.size > 0 && ProfileManager().instance.profilePosts[0].creator._id == ProfileManager().instance.idCurrentProfileUser) {
            postsList = ProfileManager().instance.profilePosts
        }
        if (ProfileManager.currentProfileUser != null && ProfileManager.currentProfileUser._id == ProfileManager().instance.idCurrentProfileUser) {
            username.text = ProfileManager.currentProfileUser.username
            description.text = ProfileManager.currentProfileUser.description
            stars.text = ProfileManager.currentProfileUser.stars.toString()
            Glide.with(activity!!.applicationContext)
                .load(AuthenticationManager.buildGlideUrlWithAuth(ProfileManager.currentProfileUser.profileUrl))
                .apply(RequestOptions.circleCropTransform()).into(profile_image)
        }
        recyclerViewAdapter = PostsAdapter(postsList)
        profilePostsRecyclerView.adapter = recyclerViewAdapter
        profilePostsRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        swipeContainer.setOnRefreshListener {
            populateData()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        populateData()
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }


}
